package lib.morkim.mfw.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.usecase.UseCase;
import lib.morkim.mfw.usecase.UseCaseProgress;
import lib.morkim.mfw.usecase.UseCaseResult;
import lib.morkim.mfw.usecase.UseCaseStateListener;
import android.app.Fragment;
import android.os.Bundle;

public abstract class Controller extends Fragment implements Observer, UseCaseStateListener {

	protected Viewable viewable;

	private AppContext appContext;

	protected Presenter presenter;

	private boolean isRegisteredToBackgroundData;

	public Controller() {
		this.presenter = createPresenter();
	}

	protected abstract Presenter createPresenter();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRetainInstance(true);

		appContext = createContext();
		presenter.initialize(appContext);
		executeInitializationTask();
	}

	protected AppContext createContext() {
		return (AppContext) getActivity().getApplicationContext();
	}
	
	void onViewableCreated(Viewable viewable) {

		this.viewable = viewable;
		this.viewable.bindUiElements();
		this.viewable.assignListeners(addListeners(new HashMap<String, ViewListener>()));
	}

	protected void executeInitializationTask() {}

	protected AppContext getAppContext() {
		return (AppContext) appContext;
	}

	protected abstract Map<String, ViewListener> addListeners(HashMap<String, ViewListener> listeners);

	public void registerUpdates() {

		if (isRegisteredToBackgroundData)
			unregisterBackgroundData();

		presenter.bindViewModel(viewable);
	}

	public void unregisterUpdates() {
		presenter.unbindViewModel();

		registerBackgroundData();

		isRegisteredToBackgroundData = true;
	}

	protected void registerBackgroundData() {}
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

	@Override
	public void onDestroy() {
		super.onDestroy();

		unregisterBackgroundData();
	}

	protected void finish() {
		viewable.finish();
	}
	
	protected void keepScreenOn(boolean keepOn) {
		((Screen) getActivity()).keepScreenOn(keepOn);
	}
}
