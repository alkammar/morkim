package lib.morkim.mfw.adapters;

import java.util.Observable;
import java.util.Observer;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.ui.MView;
import lib.morkim.mfw.ui.ViewModel;
import lib.morkim.mfw.usecase.UseCaseStateListener;

public abstract class Presenter implements Observer, UseCaseStateListener {

	private AppContext appContext;
	protected MView view;

	private Model dataModel;
	private boolean isBindedToDataModel;
	private ViewModel viewModel;

	public Presenter(AppContext appContext, MView view) {
		this.view = view;

		this.appContext = appContext;

		this.dataModel = ((MorkimApp) appContext).getModel();
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
		viewModel.register(view);
		viewModel.notifyView();
	}

	@Override
	public void update(Observable observable, Object data) {
		synchronized (this) {
			onUpdate(observable, data, viewModel);
			if (viewModel.hasListener())
				view.onModelUpdated(viewModel);
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
		return view.getStringResource(resource);
	}
}