package lib.morkim.mfw.adapters;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.ui.MView;
import lib.morkim.mfw.ui.Screen;
import lib.morkim.mfw.ui.ViewListener;
import lib.morkim.mfw.ui.ViewModel;
import lib.morkim.mfw.usecase.UseCaseAbstractFactory;
import lib.morkim.mfw.usecase.UseCaseFactory;
import lib.morkim.mfw.usecase.UseCaseResult;
import lib.morkim.mfw.usecase.UseCaseStateListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public abstract class Controller implements Observer, UseCaseStateListener {

	private AppContext appContext;
	protected MView view;

	private UseCaseAbstractFactory useCaseAbstractFactory;
	private UseCaseFactory useCaseFactory;

	private Model dataModel;
	private boolean isBindedToDataModel;

	private ViewModel viewModel;

	public Controller(AppContext appContext, MView view) {

		this.appContext = appContext;

		this.dataModel = ((MorkimApp) appContext).getModel();
		this.useCaseAbstractFactory = ((MorkimApp) appContext).getUseCaseAbstractFactory();
		this.viewModel = new ViewModel();
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

	protected void buildConfigurationModel(ViewModel viewModel) {}
	protected abstract void buildInitializationModel(ViewModel viewModel);

	public void attach(MView view, Bundle dataToRestore) {

		bindDataModel();

		this.view = view;
		this.view.bindUiElements();
		this.view.assignListeners(getListeners());

		fetchExtraData(dataToRestore);
	}

	public void bindViewModel() {

		buildConfigurationModel(viewModel);
		this.viewModel.register(view);
		this.viewModel.notifyListener();

		this.view.configureUiElements();

		buildInitializationModel(viewModel);
		this.viewModel.register(view);
		this.viewModel.notifyListener();
	}

	@Override
	public void update(Observable observable, Object data) {
		synchronized (this) {
			onUpdate(observable, data, viewModel);
			if (viewModel.hasListener())
			view.onChanged(viewModel);
		}
	}

	protected void onUpdate(Observable observable, Object data, ViewModel viewModel) {
	
	}

	public void unbindViewModel() {

		this.viewModel.unregister();
	}

	public void destroy() {
		unbindDataModel();
	}

	protected void fetchExtraData(Bundle dataToRestore) {

	}

	@Override
	public void onUseCaseComplete(UseCaseResult response) {

	}

	protected void navigateTo(Class<?> cls) {
		navigateTo(cls, Transition.NONE);
	}

	private void navigateTo(Class<?> cls, Transition transition) {

		Intent intent = new Intent(appContext.getContext(), cls);
		intent.putExtra(Screen.KEY_SCREEN_TRANSITION, transition.ordinal());
		view.startActivity(intent);
	}

	protected void navigateToGooglePlay() {
		view.startActivity(new Intent(Intent.ACTION_VIEW, Uri
				.parse(googlePlayBaseUrl()
						+ appContext.getContext().getPackageName())));
	}

	protected void showSharingList(CharSequence title) {
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(
				Intent.EXTRA_TEXT,
				Uri.parse(googlePlayBaseUrl()
						+ appContext.getContext().getPackageName()).toString());
		view.startActivity(Intent.createChooser(sharingIntent, title));
	}

	private static String googlePlayBaseUrl() {
		return "https://play.google.com/store/apps/details?id=";
	}

	protected void reloadScreen(Transition transition) {
		navigateTo(view.getClass(), transition);
		view.finish();
	}

	protected AppContext getAppContext() {
		return (AppContext) appContext;
	}

	public Model getModel() {
		return dataModel;
	}

	public UseCaseAbstractFactory getUseCaseAbstractFactory() {
		return useCaseAbstractFactory;
	}

	public UseCaseFactory getUseCaseFactory() {
		return useCaseFactory;
	}

	public void setUseCaseFactory(UseCaseFactory useCaseFactory) {
		this.useCaseFactory = useCaseFactory;
	}

	protected String string(int resource) {
		return view.getStringResource(resource);
	}

	public ViewModel getViewModel() {
		return viewModel;
	}

	public void setViewModel(ViewModel viewModel) {
		this.viewModel = viewModel;
	}

	protected abstract Map<String, ViewListener> getListeners();
}
