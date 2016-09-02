package lib.morkim.mfw.ui;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.View;

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
 * @param <VA> The {@link Presenter} associated with the Viewable
 * @param <M> The {@link Model} for this application
 * @param <A> The {@link MorkimApp} application that extends Android {@link android.app.Application}
 */
public abstract class Controller<A extends MorkimApp<M, ?>, M extends Model, VA extends ViewableActions> {

	private A morkimApp;
	protected Viewable<A, M, VA, ?, ?> viewable;

	private VA viewableActions;
	private VA emptyViewableActions;
	private boolean isViewUpdatable;

	public Controller(A morkimApp) {

		this.morkimApp = morkimApp;

		emptyViewableActions = createEmptyViewableActions();

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

	protected abstract VA createEmptyViewableActions();

	protected VA getViewableActions() {
		synchronized (this) {
			return (isViewUpdatable) ? viewableActions : emptyViewableActions;
		}
	}

	public void setViewable(Viewable<A, M, VA, ?, ?> viewable) {
		this.viewable = viewable;

		viewableActions = viewable.getActions();
	}

	protected class ViewUpdater {

	    View view;
	    ViewUpdateListener listener;

	    public ViewUpdater(View view, ViewUpdateListener listener) {
		    this.view = view;
		    this.listener = listener;
	    }
	}

	public interface ViewUpdateListener<V extends View> {
		void onUpdate(V view);
	}
}
