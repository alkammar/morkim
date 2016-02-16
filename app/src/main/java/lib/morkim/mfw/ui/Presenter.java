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

public abstract class Presenter implements Observer, UseCaseStateListener {

	private AppContext appContext;
	protected Viewable viewable;

	public Presenter(Viewable viewable) {
		
		this.viewable = viewable;
		this.appContext = viewable.getMorkimContext();
	}

	public void unbindViewModel() {

//		viewModel.unregister();
	}

	public void bindViewModel(Viewable viewable) {

//		viewModel.register(viewable);
//		viewModel.notifyView();
	}

	@Override
	public void update(Observable observable, Object data) {
		synchronized (this) {
//			onUpdate(observable, data, viewModel);
//			if (viewModel.hasListener())
//				viewModel.notifyUiView();
		}
	}

	protected void onUpdate(Observable observable, Object data,
			ViewModel viewModel) {
	}

//	public ViewModel getViewModel() {
//		return viewModel;
//	}
//
//	public void setViewModel(ViewModel viewModel) {
//		this.viewModel = viewModel;
//	}

	protected Controller getController() {
		return viewable.getController();
	}

	public AppContext getAppContext() {
		return appContext;
	}

	protected Context getContext() {
		return viewable.getContext();
	}

	public void setAppContext(AppContext appContext) {
		this.appContext = appContext;
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