package lib.morkim.mfw.adapters;

import java.util.Map;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.ui.MView;
import lib.morkim.mfw.ui.ViewListener;
import lib.morkim.mfw.usecase.UseCaseAbstractFactory;
import lib.morkim.mfw.usecase.UseCaseFactory;
import android.os.Bundle;

public abstract class Controller {

	private AppContext appContext;
	protected MView view;

	private UseCaseAbstractFactory useCaseAbstractFactory;
	private UseCaseFactory useCaseFactory;

	public Controller(AppContext appContext, MView view) {

		this.appContext = appContext;

		this.useCaseAbstractFactory = ((MorkimApp) appContext).getUseCaseAbstractFactory();
	}

	public void attach(MView view, Bundle dataToRestore) {

//		bindDataModel();

		this.view = view;
		this.view.bindUiElements();
		this.view.assignListeners(getListeners());

		fetchExtraData(dataToRestore);
	}

	protected void fetchExtraData(Bundle dataToRestore) {

	}

	protected AppContext getAppContext() {
		return (AppContext) appContext;
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

	protected abstract Map<String, ViewListener> getListeners();
}
