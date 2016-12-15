package lib.morkim.mfw.ui;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Entity;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.task.ScheduledTask;
import lib.morkim.mfw.task.UiTaskObserver;
import lib.morkim.mfw.usecase.UseCaseListener;
import lib.morkim.mfw.usecase.TaskResult;
import lib.morkim.mfw.usecase.UseCaseSubscription;
import lib.morkim.mfw.util.GenericsUtils;

/**
 * {@link Viewable} controller. Handles Viewable communication with application data model.
 * Mainly runs background tasks on data model and sends notifications on data model changes needed
 * to update the Viewable.
 * This class stays alive even if the Viewable is destroyed due to rotation. Will be destroyed when
 * its Viewable is completely destroyed.
 * @param <A> The {@link MorkimApp} application that extends Android {@link android.app.Application}
 * @param <M> The {@link Model} container for this application
 * @param <U> The {@link UpdateListener} associated with the Viewable
 */
public abstract class Controller<A extends MorkimApp<M, ?>, M extends Model, U extends UpdateListener> {

	private A morkimApp;
	protected Viewable<U, ?, ?> viewable;

	private U updateListener;
	private U pendingUpdateListener;
	private boolean isViewUpdatable;

	private boolean initializationTaskExecuted;

	private PendingEventsExecutor pendingEventsExecutor;

	public Controller() {

		pendingEventsExecutor = new PendingEventsExecutor(this);
		pendingUpdateListener = createPendingUpdateListener();
	}

	public void onAttachApp(A morkimApp) {

		this.morkimApp = morkimApp;

		subscribeUseCaseListeners();
	}

	private void subscribeUseCaseListeners() {

		Class<?> cls = getClass();

		do {
			for (Field field : cls.getDeclaredFields()) {
				if (field.isAnnotationPresent(UseCaseSubscription.class))
					try {
						field.setAccessible(true);
						morkimApp.subscribeToUseCase(
								field.getAnnotation(UseCaseSubscription.class).value(),
								(UseCaseListener<? extends TaskResult>) field.get(this));
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
			}

			cls = cls.getSuperclass();

		} while (cls != null);
	}

	@SuppressWarnings("WeakerAccess")
	protected void onExtractExtraData(@SuppressWarnings("UnusedParameters") Bundle bundledData) {}

	public void onAttachViewable(Viewable<U, ?, ?> viewable) {
		this.viewable = viewable;

		updateListener = viewable.getUpdateListener();

		Bundle bundledData = viewable.getBundledData();
		if (bundledData != null)
			onExtractExtraData(bundledData);

		if (!initializationTaskExecuted) {
			executeInitializationTask();
			initializationTaskExecuted = true;
		}
	}

	public void onInitializeViews() {}

	@SuppressWarnings("WeakerAccess")
	public void onAttachParent(@SuppressWarnings("UnusedParameters") Viewable<U, ?, ?> viewable) {}

	@SuppressWarnings("WeakerAccess")
	protected void executeInitializationTask() {}

	protected A getAppContext() {
		return morkimApp;
	}

	protected M getModel() {
		return morkimApp.getModel();
	}

	protected void finish() { viewable.finish(); }
	
	protected void keepScreenOn(boolean keepOn) {
		viewable.keepScreenOn(keepOn);
	}

    Viewable getViewable() {
        return viewable;
    }

    protected Context getContext() {
        return viewable.getContext();
    }

	protected <e extends Entity> void watchEntity(Observable observable, UiEntityObserver<e> entityObserver) {
		observable.addObserver(entityObserver.getObserver());
	}

	protected <e extends Entity> void unwatchEntity(Observable observable, UiEntityObserver<e> entityObserver) {
		observable.deleteObserver(entityObserver.getObserver());
	}

	protected <t extends ScheduledTask> void registerToTask(Class<t> task, UiTaskObserver<t> observer) {
		morkimApp.getTaskScheduler().register(task, observer);
	}

	protected <t extends ScheduledTask> void unregisterFromTask(Class<t> task, UiTaskObserver<t> observer) {
		morkimApp.getTaskScheduler().unregister(task, observer);
	}

	public void onDestroy() {
		unsubscribeUseCaseListeners();
	}

	private void unsubscribeUseCaseListeners() {
		for (Field field : getClass ().getDeclaredFields()) {
			if (field.isAnnotationPresent(UseCaseSubscription.class))
				try {
					field.setAccessible(true);
					morkimApp.unsubscribeFromUseCase(
							field.getAnnotation(UseCaseSubscription.class).value(),
							(UseCaseListener<? extends TaskResult>) field.get(this));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
		}
	}

	public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {

//		for (String permission : permissions)
//			viewable.getScreen().onPermissionRequestHandled(permission);
	}

	protected static boolean verifyPermission(int[] grantResults) {

		if (grantResults.length < 1)
			return false;

		for (int result : grantResults)
			if (result != PackageManager.PERMISSION_GRANTED)
				return false;

		return true;
	}

	void bindViews() {

		synchronized (this) {
			isViewUpdatable = true;
			viewable.onAssignListeners();
		}

		onShowViewable();
	}

	void unbindViews() {

		onHideViewable();

		synchronized (this) {
			isViewUpdatable = false;
		}
	}

	protected void onShowViewable() {
		if (pendingUpdateListener instanceof AbstractUpdateListenerPending)
			((AbstractUpdateListenerPending) pendingUpdateListener).setUpdateListener(updateListener);
		pendingEventsExecutor.onExecutePendingEvents();
	}

	@SuppressWarnings("WeakerAccess")
	protected void onHideViewable() {}

	private U createPendingUpdateListener() {

		U instance;

		Type[] actualArgs = GenericsUtils.resolveActualTypeArgs(getClass(), Controller.class);

		Class<U> cls = (Class<U>) actualArgs[2];

		instance = getAnnotatedUpdateListenerPendingImplementation(cls);

		return instance != null ? instance : generateEmptyUpdateListenerImplementation(cls);
	}

	private U getAnnotatedUpdateListenerPendingImplementation(Class<U> cls) {

		U instance = null;
		Class<U> generatedInterfaceImpl;

		try {
			// TODO need to remove dependency on static text "Pending"
			// TODO need to get package name from compiler project
			generatedInterfaceImpl = (Class<U>) Class.forName("lib.morkim.mfw.generated.update.listeners." + cls.getSimpleName() + "Pending");
			Log.i("Controller", generatedInterfaceImpl.toString());
			instance = generatedInterfaceImpl.newInstance();
			((AbstractUpdateListenerPending) instance).setPendingEventsExecutor(pendingEventsExecutor);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return instance;
	}

	private U generateEmptyUpdateListenerImplementation(Class<U> cls) {
		U instance;InvocationHandler handler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println(method.getName());
				return null;
			}
		};

		instance = (U) Proxy.newProxyInstance(cls.getClassLoader(), new Class<?>[]{cls}, handler);

		return instance;
	}

	protected U getUpdateListener() {
		synchronized (this) {
			return (isViewUpdatable) ? updateListener : pendingUpdateListener;
		}
	}

	@SuppressWarnings("WeakerAccess")
	public class PendingEventsExecutor {

		@SuppressWarnings("unused")
		private Controller controller;

		private List<PendingEvent> pendingEvents;

		PendingEventsExecutor(Controller controller) {

			this.controller = controller;

			pendingEvents = new ArrayList<>();
		}

		public void add(PendingEvent pendingEvent) {

			synchronized (Controller.this) {
				if (isViewUpdatable)
					pendingEvent.onExecuteWhenUiAvailable();
				else
					pendingEvents.add(pendingEvent);
			}
		}

		void onExecutePendingEvents() {

			List<PendingEvent> consumedEvents = new ArrayList<>();

			for (PendingEvent pendingEvent : pendingEvents) {
				pendingEvent.onExecuteWhenUiAvailable();
				consumedEvents.add(pendingEvent);
			}

			for (PendingEvent pendingEvent : consumedEvents) {
				pendingEvents.remove(pendingEvent);
			}
		}
	}

	public interface PendingEvent {

		void onExecuteWhenUiAvailable();
	}
}
