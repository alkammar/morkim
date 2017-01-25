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
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Entity;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.task.ScheduledTask;
import lib.morkim.mfw.task.UiTaskObserver;
import lib.morkim.mfw.usecase.TaskResult;
import lib.morkim.mfw.usecase.UseCaseListener;
import lib.morkim.mfw.usecase.UseCaseSubscription;
import lib.morkim.mfw.util.GenericsUtils;

/**
 * Handles {@link Viewable} updates and events. Routing events to as well as receiving updates from
 * underlying layer.
 * Stays alive even if the Viewable is destroyed due to rotation. Will only be destroyed when its
 * viewable is completely destroyed (e.g. when viewable is removed by pressing back button or killed
 * by OS).
 * Can be used to cache viewable's data model.
 * Controller handles the updates to your viewable, updating your viewable only when it is in the
 * correct viewing state.
 * Keeps states all the updates to viewable (e.g. you can safely update an activity in the stopped
 * state without losing state or data).
 *
 * @param <A> The {@link MorkimApp} application that extends Android {@link android.app.Application}
 * @param <M> The {@link Model} cached data model container for this application
 * @param <U> The {@link UpdateListener} associated with the viewable
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

	/**
	 * Called when creating the controller. At this point the controller has a {@link MorkimApp}
	 * reference.
	 * For Morkim Use Case layer, this is where all defined {@link UseCaseListener}s get registered to
	 * their respective use cases.
	 *
	 * @param morkimApp morkim application
	 */
	public void onAttachApp(A morkimApp) {

		this.morkimApp = morkimApp;

		subscribeUseCaseListeners();
	}

	// TODO this needs to be put in some utility to be available for other classes like application and use case
	private void subscribeUseCaseListeners() {

		Class<?> cls = getClass();

		do {
			for (Field field : cls.getDeclaredFields()) {
				if (field.isAnnotationPresent(UseCaseSubscription.class))
					try {
						field.setAccessible(true);
						morkimApp.getUseCaseManager().subscribeToUseCase(
								field.getAnnotation(UseCaseSubscription.class).value(),
								(UseCaseListener<? extends TaskResult>) field.get(this));
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
			}

			cls = cls.getSuperclass();

		} while (cls != null);
	}

	/**
	 * Called when a {@link Viewable} is attached to the controller. Viewable can be attached more
	 * than once due to screen rotation.
	 *
	 * @param viewable viewable
	 */
	public void onAttachViewable(Viewable<U, ?, ?> viewable) {
		this.viewable = viewable;

		updateListener = viewable.getUpdateListener();

		Bundle bundledData = viewable.getBundledData();
		if (bundledData != null)
			onExtractExtraData(bundledData);

		if (!initializationTaskExecuted) {

			onExecuteInitializationTasks();
			initializationTaskExecuted = true;
		}
	}

	/**
	 * Called when creating the {@link Viewable} for fetching data passed to component.
	 *
	 * @param bundledData data
	 */
	@SuppressWarnings("WeakerAccess")
	protected void onExtractExtraData(@SuppressWarnings("UnusedParameters") Bundle bundledData) {}

	/**
	 * Called when creating the controller. This is a convenient place to run initialization
	 * background tasks (e.g. async data loaders or backend calls for initializing controller's data).
	 */
	@SuppressWarnings("WeakerAccess")
	protected void onExecuteInitializationTasks() {}

	/**
	 * Called every time a {@link Viewable} is attached to the controller. This is a typical place to
	 * initialize the viewable with whatever data available as the viewable will be ready to be
	 * visible shortly.
	 * Use {@link #getUpdateListener()} method to get reference to the viewable update interface.
	 */
	public void onInitializeViews() {}

	/**
	 * Called when the {@link Viewable} is attached to its parent. This is typical for fragment
	 * viewable, where a fragment will have either a parnt activity or a parent fragment.
	 * This method is mainly to get reference to viewable's parent as a listener interface.
	 * This is useful if the controller wants to communicate events to parent viewable.
	 * To achieve this use {@link Viewable#getParentListener()}
	 *
	 * @param viewable viewable
	 */
	@SuppressWarnings("WeakerAccess")
	public void onAttachParent(@SuppressWarnings("UnusedParameters") Viewable<U, ?, ?> viewable) {}

	protected A getAppContext() {
		return morkimApp;
	}

	protected M getModel() {
		return morkimApp.getModel();
	}

	/**
	 * Calls the finish method of the {@link Viewable}. Use this to force the view to terminate itself.
	 */
	protected void finish() { viewable.finish(); }

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

	/**
	 * Called when the controller is being destroyed.
	 */
	public void onDestroy() {
		unsubscribeUseCaseListeners();
	}

	// TODO this needs to be put in some utility to be available for other classes like application and use case
	private void unsubscribeUseCaseListeners() {
		for (Field field : getClass ().getDeclaredFields()) {
			if (field.isAnnotationPresent(UseCaseSubscription.class))
				try {
					field.setAccessible(true);
					morkimApp.getUseCaseManager().unsubscribeFromUseCase(
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

		if (!isViewUpdatable) {

			synchronized (this) {
				isViewUpdatable = true;
				viewable.onAssignListeners();
			}

			onShowViewable();
		}
	}

	void unbindViews() {

		if (isViewUpdatable) {

			onHideViewable();

			synchronized (this) {
				isViewUpdatable = false;
			}
		}
	}

	/**
	 * Called when the {@link Viewable} is visible.
	 */
	protected void onShowViewable() {
		if (pendingUpdateListener instanceof AbstractUpdateListenerPending)
			//noinspection unchecked
			((AbstractUpdateListenerPending) pendingUpdateListener).setUpdateListener(updateListener);
		pendingEventsExecutor.onExecutePendingEvents();
	}

	/**
	 * Called when the {@link Viewable} is no longer visible
	 */
	@SuppressWarnings("WeakerAccess")
	protected void onHideViewable() {}

	private U createPendingUpdateListener() {

		U instance;

		Type[] actualArgs = GenericsUtils.resolveActualTypeArgs(getClass(), Controller.class);

		Class<U> cls;
		Type actualUpdateListener = actualArgs[2];
		if (actualUpdateListener instanceof TypeVariable)
			//noinspection unchecked
			cls = (Class<U>) ((TypeVariable) actualUpdateListener).getBounds()[0];
		else
			//noinspection unchecked
			cls = (Class<U>) actualUpdateListener;

		instance = getAnnotatedUpdateListenerPendingImplementation(cls);

		return instance != null ? instance : generateEmptyUpdateListenerImplementation(cls);
	}

	private U getAnnotatedUpdateListenerPendingImplementation(Class<U> cls) {

		U instance = null;
		Class<U> generatedInterfaceImpl;

		try {
			// TODO need to remove dependency on static text "Pending"
			// TODO need to get package name from compiler project
			//noinspection unchecked
			generatedInterfaceImpl = (Class<U>) Class.forName("lib.morkim.mfw.generated.update.listeners." + cls.getSimpleName() + "Pending");
			Log.i("Controller", generatedInterfaceImpl.toString());
			instance = generatedInterfaceImpl.newInstance();
			((AbstractUpdateListenerPending) instance).setPendingEventsExecutor(pendingEventsExecutor);
		} catch (ClassNotFoundException e) {
			// This viewable does not have a pending update interface
		} catch (IllegalAccessException e) {
			throw new Error("Unable to access member");
		} catch (InstantiationException e) {
			throw new Error("Unable to access default constructor");
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

		//noinspection unchecked
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
