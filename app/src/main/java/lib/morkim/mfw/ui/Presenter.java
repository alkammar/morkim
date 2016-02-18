package lib.morkim.mfw.ui;

import android.content.Context;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.domain.Model;

public abstract class Presenter<C extends  Controller> {

	private AppContext appContext;
	protected Viewable<C, ? extends Presenter> viewable;

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

	protected Model getModel() {
		return appContext.getModel();
	}
}