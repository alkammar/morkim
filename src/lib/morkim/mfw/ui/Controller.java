package lib.morkim.mfw.ui;

import java.util.Map;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.usecase.UseCase;
import lib.morkim.mfw.usecase.UseCaseFactory;
import lib.morkim.mfw.usecase.UseCaseStateListener;
import android.os.Bundle;

public abstract class Controller {

	protected Viewable viewable;

	private AppContext appContext;
	private UseCaseFactory useCaseFactory;
	
	private UseCaseStateListener useCaseListener;

	public Controller(AppContext appContext, Viewable viewable) {

		this.appContext = appContext;
	}

	public void attach(Viewable viewable, Bundle dataToRestore) {

//		bindDataModel();

		this.viewable = viewable;
		this.viewable.bindUiElements();
		this.viewable.assignListeners(getListeners());
		
		this.useCaseListener = viewable.getUseCaseListener();
		this.useCaseFactory = appContext.getUseCaseFactory();

		fetchExtraData(dataToRestore);
	}

	protected void fetchExtraData(Bundle dataToRestore) {

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
		useCase.setListener(useCaseListener);
		return useCase;
	}

	protected abstract Map<String, ViewListener> getListeners();

	public void onDestroy() {}
}
