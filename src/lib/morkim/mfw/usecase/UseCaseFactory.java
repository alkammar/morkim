package lib.morkim.mfw.usecase;

import lib.morkim.mfw.app.AppContext;

public abstract class UseCaseFactory {
	
	private AppContext appContext;

	public UseCaseFactory(AppContext appContext) {
		this.appContext = appContext;
	}

	public abstract UseCase createUseCase(String name);

	public AppContext getAppContext() {
		return appContext;
	}

	public void setAppContext(AppContext appContext) {
		this.appContext = appContext;
	}
}
