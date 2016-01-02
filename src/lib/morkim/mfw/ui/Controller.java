package lib.morkim.mfw.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.usecase.UseCase;
import lib.morkim.mfw.usecase.UseCaseFactory;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

public abstract class Controller extends Fragment implements Observer {

	protected Viewable viewable;

	private AppContext appContext;
	private UseCaseFactory useCaseFactory;

	protected Presenter presenter;

	public Controller() {
		this.presenter = createPresenter();
	}

	protected abstract Presenter createPresenter();

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		this.viewable = (Viewable) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRetainInstance(true);

		appContext = (AppContext) getActivity().getApplicationContext();
		useCaseFactory = appContext.getUseCaseFactory();
		presenter.initialize(appContext);
		executeInitializationTask();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		this.viewable.bindUiElements();
		this.viewable.assignListeners(addListeners(new HashMap<String, ViewListener>()));
	}

	protected void executeInitializationTask() {

	}

	protected AppContext getAppContext() {
		return (AppContext) appContext;
	}

	public UseCaseFactory getUseCaseFactory() {
		return useCaseFactory;
	}

	public void setUseCaseFactory(UseCaseFactory useCaseFactory) {
		this.useCaseFactory = useCaseFactory;
	}
	
	public UseCase createUseCase(String name) {
		UseCase useCase = useCaseFactory.createUseCase(name);
		useCase.setListener(presenter);
		return useCase;
	}

	protected abstract Map<String, ViewListener> addListeners(HashMap<String, ViewListener> listeners);

	public void registerUpdates() {
		presenter.bindViewModel(viewable);
	}

	public void unregisterUpdates() {
		presenter.unbindViewModel();
	}
	
	protected Model getModel() {
		return appContext.getModel();
	}
	
	@Override
	public void update(Observable observable, Object data) {}
}
