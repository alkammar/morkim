package lib.morkim.mfw.ui;

import android.content.Context;

import java.util.Observable;
import java.util.Observer;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.usecase.UseCase;
import lib.morkim.mfw.usecase.UseCaseProgress;
import lib.morkim.mfw.usecase.UseCaseResponse;
import lib.morkim.mfw.usecase.UseCaseStateListener;

public abstract class Controller<P extends Presenter, M extends Model, A extends MorkimApp<M, ?>> extends Observable
		implements Observer, UseCaseStateListener<UseCaseResponse> {

	private A morkimApp;
	protected Viewable<A, ?, P> viewable;

	private boolean isRegisteredToBackgroundData;

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

	protected void unregisterBackgroundData() {}

	protected M getModel() {
		return morkimApp.getModel();
	}

	@Override
	public void update(Observable observable, Object data) {}

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

	@Override
	public void addObserver(Observer observer) {
		super.addObserver(observer);

		observer.update(this, null);
	}

	public void destroy() {

	}
}
