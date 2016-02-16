package lib.morkim.mfw.ui;

import android.content.Context;

import lib.morkim.mfw.app.AppContext;

public abstract class Presenter {

	private AppContext appContext;
	protected Viewable viewable;

	public Presenter(Viewable viewable) {
		
		this.viewable = viewable;
		this.appContext = viewable.getMorkimContext();
	}

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
}