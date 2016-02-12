package lib.morkim.mfw.ui;

import android.content.Context;

import java.util.Observable;
import java.util.Observer;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.usecase.UseCase;
import lib.morkim.mfw.usecase.UseCaseProgress;
import lib.morkim.mfw.usecase.UseCaseResult;
import lib.morkim.mfw.usecase.UseCaseStateListener;

public abstract class Controller implements Observer, UseCaseStateListener {

	protected Viewable viewable;
	private AppContext appContext;

	private boolean isRegisteredToBackgroundData;

	public Controller(Viewable viewable) {
		this.viewable = viewable;

        appContext = createContext();
        executeInitializationTask();
	}

	protected AppContext createContext() {
		return viewable.getMorkimContext();
	}

	protected void executeInitializationTask() {}

	protected AppContext getAppContext() {
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
	public void onUseCaseComplete(UseCaseResult response) {}

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

    Context getContext() {
        return viewable.getContext();
    }
}
