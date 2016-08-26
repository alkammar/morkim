package lib.morkim.mfw.ui;

import android.content.Context;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;

public abstract class Presenter<C extends  Controller, M extends Model, A extends MorkimApp<M, ?>> {

	private A morkimApp;
	protected Viewable<A, C, ?> viewable;

	protected C controller;

	public Presenter(Viewable<A, C, ?> viewable) {
		
		this.viewable = viewable;
		this.morkimApp = viewable.getMorkimContext();
	}

	public MorkimApp getAppContext() {
		return morkimApp;
	}

	protected Context getContext() {
		return viewable.getContext();
	}

	public void setAppContext(A morkimApp) {
		this.morkimApp = morkimApp;
	}

	protected M getModel() {
		return morkimApp.getModel();
	}

	void setController(C controller) {
		this.controller = controller;
	}
}