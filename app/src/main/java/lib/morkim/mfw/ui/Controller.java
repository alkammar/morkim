package lib.morkim.mfw.ui;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.SparseArray;
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
 * @param <P> The {@link Presenter} associated with the Viewable
 * @param <M> The {@link Model} for this application
 * @param <A> The {@link MorkimApp} application that extends Android {@link android.app.Application}
 */
public abstract class Controller<P extends Presenter, M extends Model, A extends MorkimApp<M, ?>> {

	protected SparseArray<ViewUpdater> viewUpdaterArray;
	private A morkimApp;
	protected Viewable<A, ?, P> viewable;
	protected P presenter;

	public Controller(Viewable<A, ?, P> viewable) {

		viewUpdaterArray = new SparseArray<>();

		this.viewable = viewable;

        morkimApp = createContext();

		onExtractExtraData();

        executeInitializationTask();
	}

	public void attachViewable(Viewable<A, ?, P> viewable) {
		this.viewable = viewable;
		this.presenter = viewable.getPresenter();
		this.presenter.setController(this);
	}

	protected void onExtractExtraData() {

	}

	protected A createContext() {
		return viewable.getMorkimContext();
	}

	protected void executeInitializationTask() {}

	protected A getAppContext() {
		return morkimApp;
	}

	protected M getModel() {
		return morkimApp.getModel();
	}

	protected void finish() {
		viewable.finish();
	}
	
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
						onModelUpdated(observable, data);
					}
				});
		}
	};

	protected void onModelUpdated(Observable observable, Object data) {}

	protected void watchModel(Observable observable) {
		observable.addObserver(modelObserver);
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

		for (String permission : permissions)
			viewable.getScreen().onPermissionRequestHandled(permission);
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
			onBindViews();
		}

		onInitViews();
	}

	protected abstract void onBindViews();

	public void unbindViews() {

		synchronized (this) {
			viewUpdaterArray.clear();
		}
	}

	protected void onUnBindViews() {}

	protected void bindUpdateListener(int id, ViewUpdateListener listener) {

	    View view = getViewById(id);
	    viewUpdaterArray.put(id, new ViewUpdater(view, listener));
	}

	protected abstract View getViewById(int id);

	protected void onInitViews() {

	}

	protected void notifyView(int id) {
	    ViewUpdater viewUpdater = viewUpdaterArray.get(id);

	    if (viewUpdater != null) {
	        View view = viewUpdater.view;
		    //noinspection unchecked
		    viewUpdater.listener.onUpdate(view);
	    }
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
