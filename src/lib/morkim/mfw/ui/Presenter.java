package lib.morkim.mfw.ui;

import java.util.Observable;
import java.util.Observer;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.usecase.UseCase;
import lib.morkim.mfw.usecase.UseCaseProgress;
import lib.morkim.mfw.usecase.UseCaseResult;
import lib.morkim.mfw.usecase.UseCaseStateListener;

public abstract class Presenter implements Observer, UseCaseStateListener {

	private AppContext appContext;
	protected Viewable viewable;

	private ViewModel viewModel;

	public Presenter(Viewable viewable) {
		
		this.viewable = viewable;
		viewModel = new ViewModel();
	}
	
	void initialize(AppContext appContext) {

		this.appContext = appContext;

		initializeViewModel(viewModel);
	}

	protected abstract void initializeViewModel(ViewModel viewModel);

	public void unbindViewModel() {

		viewModel.unregister();
	}

	public void bindViewModel(Viewable viewable) {
		
		viewModel.register(viewable);
		viewModel.notifyView();
	}

	@Override
	public void update(Observable observable, Object data) {
		synchronized (this) {
			onUpdate(observable, data, viewModel);
			if (viewModel.hasListener())
				viewModel.notifyUiView();
		}
	}

	protected void onUpdate(Observable observable, Object data,
			ViewModel viewModel) {
	}

	public ViewModel getViewModel() {
		return viewModel;
	}

	public void setViewModel(ViewModel viewModel) {
		this.viewModel = viewModel;
	}

	public AppContext getAppContext() {
		return appContext;
	}

	public void setAppContext(AppContext appContext) {
		this.appContext = appContext;
	}

	protected String string(int resource) {
		return ((MorkimApp) appContext).getString(resource);
	}
	
	protected Model getModel() {
		return appContext.getModel();
	}

	@Override
	public void onUseCaseStart(UseCase useCase) {}

	@Override
	public void onUseCaseUpdate(UseCaseProgress response) {}

	@Override
	public void onUseCaseComplete(UseCaseResult response) {}

	@Override
	public void onUseCaseCancel() {}
}