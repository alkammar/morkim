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

public abstract class Controller<P extends Presenter> extends Observable
		implements Observer, UseCaseStateListener<UseCaseResponse> {

	protected Viewable<?, P> viewable;
	private MorkimApp appContext;

	private boolean isRegisteredToBackgroundData;

	public Controller(Viewable<?, P> viewable) {
		this.viewable = viewable;

        appContext = createContext();

		onExtractExtraData();

        executeInitializationTask();
	}

	protected void onExtractExtraData() {

	}

	protected MorkimApp createContext() {
		return viewable.getMorkimContext();
	}

	protected void executeInitializationTask() {}

	protected MorkimApp getAppContext() {
		return appContext;
	}

	protected void unregisterBackgroundData() {}

	protected Model getModel() {
		return appContext.getModel();
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

    Viewable<?, P> getViewable() {
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
