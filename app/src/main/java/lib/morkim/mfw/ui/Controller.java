package lib.morkim.mfw.ui;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Entity;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.task.ScheduledTask;
import lib.morkim.mfw.task.UiTaskObserver;

/**
 * {@link Viewable} controller. Handles Viewable communication with application data model.
 * Mainly runs background tasks on data model and sends notifications on data model changes needed
 * to update the Viewable.
 * This class stays alive even if the Viewable is destroyed due to rotation. Will be destroyed when
 * its Viewable is completely destroyed.
 * @param <V> The {@link Presenter} associated with the Viewable
 * @param <M> The {@link Model} for this application
 * @param <A> The {@link MorkimApp} application that extends Android {@link android.app.Application}
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class Controller<A extends MorkimApp<M, ?>, M extends Model, V extends UpdateListener> {

	private A morkimApp;
	protected Viewable<V, ?, ?> viewable;

	private V updateListener;
	private V emptyUpdateListener;
	private boolean isViewUpdatable;

	private boolean initializationTaskExecuted;

	protected PendingEventsExecutor pendingEventsExecutor;

	public Controller(A morkimApp) {

		pendingEventsExecutor = new PendingEventsExecutor(this);

		emptyUpdateListener = createStubUpdateListener();
		this.morkimApp = morkimApp;
	}

	protected void onExtractExtraData(Bundle bundledData) {}

	public void onAttachViewable(Viewable<V, ?, ?> viewable) {
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

	public void onInitializeViews(V updateListener) {}

	public void onAttachParent(Viewable<V, ?, ?> viewable) {}

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

	protected <E extends Entity> void watchEntity(Observable observable, UiEntityObserver<E> entityObserver) {
		observable.addObserver(entityObserver.getObserver());
	}

	protected <E extends Entity> void unwatchEntity(Observable observable, UiEntityObserver<E> entityObserver) {
		observable.deleteObserver(entityObserver.getObserver());
	}

	protected <T extends ScheduledTask> void registerToTask(Class<T> task, UiTaskObserver<T> observer) {
		morkimApp.getTaskScheduler().register(task, observer);
	}

	protected <T extends ScheduledTask> void unregisterFromTask(Class<T> task, UiTaskObserver<T> observer) {
		morkimApp.getTaskScheduler().unregister(task, observer);
	}

	public void onDestroy() {

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
		if (emptyUpdateListener instanceof AbstractUpdateListenerPending)
			((AbstractUpdateListenerPending) emptyUpdateListener).setUpdateListener(updateListener);
		pendingEventsExecutor.onExecutePendingEvents();
	}

	protected void onHideViewable() {}

	private V createStubUpdateListener() {

		V instance;

		Type[] actualArgs = resolveActualTypeArgs(getClass(), Controller.class);

		Class<V> cls = (Class<V>) actualArgs[2];

		instance = getAnnotatedUpdateListenerPendingImplementation(cls);

		return instance != null ? instance : generateEmptyUpdateListenerImplementation(cls);
	}

	private V getAnnotatedUpdateListenerPendingImplementation(Class<V> cls) {

		V instance = null;
		Class<V> generatedInterfaceImpl;

		try {
			generatedInterfaceImpl = (Class<V>) Class.forName("lib.morkim.mfw.generated.update.listeners." + cls.getSimpleName() + "Pending");
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

	private V generateEmptyUpdateListenerImplementation(Class<V> cls) {
		V instance;InvocationHandler handler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println(method.getName());
				return null;
			}
		};

		instance = (V) Proxy.newProxyInstance(cls.getClassLoader(), new Class<?>[]{cls}, handler);

		return instance;
	}

	/**
	 * Resolves the actual generic type arguments for a base class, as viewed from a subclass or implementation.
	 *
	 * @param <T> base type
	 * @param offspring class or interface subclassing or extending the base type
	 * @param base base class
	 * @param actualArgs the actual type arguments passed to the offspring class
	 * @return actual generic type arguments, must match the type parameters of the offspring class. If omitted, the
	 * type parameters will be used instead.
	 */
	public static <T> Type[] resolveActualTypeArgs (Class<? extends T> offspring, Class<T> base, Type... actualArgs) {

		assert offspring != null;
		assert base != null;
		assert actualArgs.length == 0 || actualArgs.length == offspring.getTypeParameters().length;

		//  If actual types are omitted, the type parameters will be used instead.
		if (actualArgs.length == 0) {
			actualArgs = offspring.getTypeParameters();
		}
		// map type parameters into the actual types
		Map<String, Type> typeVariables = new HashMap<String, Type>();
		for (int i = 0; i < actualArgs.length; i++) {
			TypeVariable<?> typeVariable = (TypeVariable<?>) offspring.getTypeParameters()[i];
			typeVariables.put(typeVariable.getName(), actualArgs[i]);
		}

		// Find direct ancestors (superclass, interfaces)
		List<Type> ancestors = new LinkedList<Type>();
		if (offspring.getGenericSuperclass() != null) {
			ancestors.add(offspring.getGenericSuperclass());
		}
		for (Type t : offspring.getGenericInterfaces()) {
			ancestors.add(t);
		}

		// Recurse into ancestors (superclass, interfaces)
		for (Type type : ancestors) {
			if (type instanceof Class<?>) {
				// ancestor is non-parameterized. Recurse only if it matches the base class.
				Class<?> ancestorClass = (Class<?>) type;
				if (base.isAssignableFrom(ancestorClass)) {
					Type[] result = resolveActualTypeArgs((Class<? extends T>) ancestorClass, base);
					if (result != null) {
						return result;
					}
				}
			}
			if (type instanceof ParameterizedType) {
				// ancestor is parameterized. Recurse only if the raw type matches the base class.
				ParameterizedType parameterizedType = (ParameterizedType) type;
				Type rawType = parameterizedType.getRawType();
				if (rawType instanceof Class<?>) {
					Class<?> rawTypeClass = (Class<?>) rawType;
					if (base.isAssignableFrom(rawTypeClass)) {

						// loop through all type arguments and replace type variables with the actually known types
						List<Type> resolvedTypes = new LinkedList<Type>();
						for (Type t : parameterizedType.getActualTypeArguments()) {
							if (t instanceof TypeVariable<?>) {
								Type resolvedType = typeVariables.get(((TypeVariable<?>) t).getName());
								resolvedTypes.add(resolvedType != null ? resolvedType : t);
							} else {
								resolvedTypes.add(t);
							}
						}

						Type[] result = resolveActualTypeArgs((Class<? extends T>) rawTypeClass, base, resolvedTypes.toArray(new Type[] {}));
						if (result != null) {
							return result;
						}
					}
				}
			}
		}

		// we have a result if we reached the base class.
		return offspring.equals(base) ? actualArgs : null;
	}

	protected V getUpdateListener() {
		synchronized (this) {
			return (isViewUpdatable) ? updateListener : emptyUpdateListener;
		}
	}

	public class PendingEventsExecutor {

		private Controller controller;

		private List<PendingEvent> pendingEvents;

		public PendingEventsExecutor(Controller controller) {

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
