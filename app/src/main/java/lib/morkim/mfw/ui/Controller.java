package lib.morkim.mfw.ui;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
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
public abstract class Controller<A extends MorkimApp<M, ?>, M extends Model, V extends UpdateListener> {

	private A morkimApp;
	protected Viewable<A, M, V, ?, ?> viewable;

	private V updateListener;
	private V emptyUpdateListener;
	private boolean isViewUpdatable;

	private boolean initializationTaskExecuted;

	public Controller(A morkimApp) {

		emptyUpdateListener = createEmptyViewableUpdate();
		this.morkimApp = morkimApp;
	}

	protected void onExtractExtraData(Bundle bundledData) {}

	public void onAttachViewable(Viewable<A, M, V, ?, ?> viewable) {
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

	protected abstract A createContext();

	protected void executeInitializationTask() {}

	protected A getAppContext() {
		return morkimApp;
	}

	protected M getModel() {
		return morkimApp.getModel();
	}

	protected void finish() {}
	
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

	public void bindViews() {

		synchronized (this) {
			isViewUpdatable = true;
			viewable.onBindViews();
		}

		onInitViews();
	}

	public void unbindViews() {

		synchronized (this) {
			isViewUpdatable = false;
		}
	}

	protected void onUnBindViews() {}

	protected void onInitViews() {

	}

	private V createEmptyViewableUpdate() {

		V instance = null;

		Class<V> cls = (Class<V>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[2];

		InvocationHandler handler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println(method.getName());
				return null;
			}
		};

		instance = (V) Proxy.newProxyInstance(cls.getClassLoader(), new Class<?>[] { cls }, handler);

		return instance;
	}

	protected V getUpdateListener() {
		synchronized (this) {
			return (isViewUpdatable) ? updateListener : emptyUpdateListener;
		}
	}

	public abstract Activity getActivity();
}
