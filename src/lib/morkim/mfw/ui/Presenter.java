package lib.morkim.mfw.ui;

import java.util.Observable;
import java.util.Observer;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.usecase.UseCaseStateListener;

public abstract class Presenter implements Observer, UseCaseStateListener {

	private AppContext appContext;
	protected Viewable viewable;

	private Model dataModel;
	private boolean isBindedToDataModel;
	private ViewModel viewModel;

	public Presenter(AppContext appContext, Viewable viewable) {
		this.viewable = viewable;

		this.appContext = appContext;

		this.dataModel = ((AppContext) appContext).getModel();
		viewModel = new ViewModel();
	}

	protected void buildConfigurationModel(ViewModel viewModel) {}

	protected abstract void buildInitializationModel(ViewModel viewModel);

	public void unbindViewModel() {

		viewModel.unregister();
	}

	public void destroy() {
		unbindDataModel();
	}

	private void bindDataModel() {
		synchronized (this) {
			if (!isBindedToDataModel) {
				onBindDataModel();
				isBindedToDataModel = true;
			}
		}
	}

	private void unbindDataModel() {
		synchronized (this) {
			if (isBindedToDataModel) {
				onUnbindDataModel();
				isBindedToDataModel = false;
			}
		}
	}

	protected void onBindDataModel() {
		this.dataModel.addObserver(this);
	}

	protected void onUnbindDataModel() {
		this.dataModel.deleteObserver(this);
	}

	public void bindViewModel() {

		buildInitializationModel(viewModel);
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

	public Model getModel() {
		return dataModel;
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
		return viewable.getStringResource(resource);
	}
}