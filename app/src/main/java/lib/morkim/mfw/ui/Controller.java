package lib.morkim.mfw.ui;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.View;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Observable;
import java.util.Observer;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Entity;
import lib.morkim.mfw.domain.Model;

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

	public Controller(A morkimApp) {

		emptyUpdateListener = createEmptyViewableUpdate();
		this.morkimApp = morkimApp;

		onExtractExtraData();

        executeInitializationTask();
	}

	protected void onExtractExtraData() {

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
	
	public void onDialogPositive(String tag) {
		
	}
	
	public void onDialogNegative(String tag) {
		
	}

    Viewable getViewable() {
        return viewable;
    }

    protected Context getContext() {
        return viewable.getContext();
    }

	private Observer modelObserver = new Observer() {
		@Override
		public void update(final Observable observable, final Object data) {

			if (observable instanceof Entity)
				((Activity) viewable).runOnUiThread(new Runnable() {
					@Override
					public void run() {
						onModelUpdated((Entity) observable, data);
					}
				});
		}
	};

	protected <E extends Entity> void onModelUpdated(E observable, Object data) {}

	protected void watchEntity(Observable observable) {
		observable.addObserver(modelObserver);
	}

	protected <E extends Entity> void watchEntity(Observable observable, UiEntityObserver<E> observer) {
		observable.addObserver(observer);
	}

	protected void unwatchModel(Observable observable) {
		observable.deleteObserver(modelObserver);
	}

	protected void registerToTask(String task) {
		morkimApp.getTaskScheduler().register(task, modelObserver);
	}

	protected void unregisterFromTask(String task) {
		morkimApp.getTaskScheduler().unregister(task, modelObserver);
	}

	public void destroy() {

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

	protected abstract View getViewById(int id);

	protected void onInitViews() {

	}

	private V createEmptyViewableUpdate() {

		V instance = null;

		try {
			Class<V> cls = (Class<V>) Class.forName(getUpdateListenerClass().getName());

			InvocationHandler handler = new InvocationHandler() {
				@Override
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					System.out.println(method.getName());
					return null;
				}
			};

			instance = (V) Proxy.newProxyInstance(cls.getClassLoader(), new Class<?>[] { cls }, handler);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return instance;
	}

	protected abstract Class<V> getUpdateListenerClass();

	protected V getUpdateListener() {
		synchronized (this) {
			return (isViewUpdatable) ? updateListener : emptyUpdateListener;
		}
	}

	public void setViewable(Viewable<A, M, V, ?, ?> viewable) {
		this.viewable = viewable;

		updateListener = viewable.getUpdateListener();
	}
}
