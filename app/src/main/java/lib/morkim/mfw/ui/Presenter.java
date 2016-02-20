package lib.morkim.mfw.ui;

import android.content.Context;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;

public abstract class Presenter<C extends  Controller> {

	private MorkimApp appContext;
	protected Viewable<C, ?> viewable;

	public Presenter(Viewable<C, ?> viewable) {
		
		this.viewable = viewable;
		this.appContext = viewable.getMorkimContext();
	}

	protected C getController() {
		return viewable.getController();
	}

	public MorkimApp getAppContext() {
		return appContext;
	}

	protected Context getContext() {
		return viewable.getContext();
	}

	public void setAppContext(MorkimApp appContext) {
		this.appContext = appContext;
	}

	protected Model getModel() {
		return appContext.getModel();
	}
}