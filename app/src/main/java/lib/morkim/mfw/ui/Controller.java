package lib.morkim.mfw.ui;

import android.content.Context;

import java.util.Observable;
import java.util.Observer;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Entity;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.usecase.UseCase;
import lib.morkim.mfw.usecase.UseCaseProgress;
import lib.morkim.mfw.usecase.UseCaseResponse;
import lib.morkim.mfw.usecase.UseCaseStateListener;

public abstract class Controller<P extends Presenter, M extends Model, A extends MorkimApp<M, ?>> extends Observable
		implements UseCaseStateListener<UseCaseResponse> {

	private A morkimApp;
	protected Viewable<A, ?, P> viewable;

	public Controller(Viewable viewable) {
		this.viewable = viewable;

        morkimApp = createContext();

		onExtractExtraData();

        executeInitializationTask();
	}

	public void attachViewable(Viewable viewable) {
		this.viewable = viewable;
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

	@Override
	public void onUseCaseStart(UseCase useCase) {}

	@Override
	public void onUseCaseUpdate(UseCaseProgress response) {}

	@Override
	public void onUseCaseComplete(UseCaseResponse response) {}

	@Override
	public void onUseCaseCancel() {}

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

	protected P getPresenter() {
		return viewable.getPresenter();
	}

	private Observer modelObserver = new Observer() {
		@Override
		public void update(Observable observable, Object data) {

			if (observable instanceof Entity)
				viewable.getScreen().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						onModelUpdated();
					}
				});
		}
	};

	protected void onModelUpdated() {

	}

	protected void observeModel(Observable observable) {
		observable.addObserver(modelObserver);
	}

	void registerForUpdates(Observer observer) {

		addObserver(observer);
		observer.update(this, null);
	}

	void unregisterFromUpdates(Observer observer) {
		synchronized (this) {
			deleteObserver(observer);
		}
	}

	protected void notifyRegistered() {
		setChanged();
		notifyObservers();
	}

	public void destroy() {

	}
}
