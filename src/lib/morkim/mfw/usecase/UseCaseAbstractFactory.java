package lib.morkim.mfw.usecase;

import lib.morkim.mfw.app.AppContext;

public abstract class UseCaseAbstractFactory {
	
	private AppContext appContext;

	public UseCaseAbstractFactory(AppContext appContext) {
		this.setAppContext(appContext);
	}
	
	public abstract UseCaseFactory createUseCaseFactory(String factory);

	public AppContext getAppContext() {
		return appContext;
	}

	public void setAppContext(AppContext appContext) {
		this.appContext = appContext;
	}
}
