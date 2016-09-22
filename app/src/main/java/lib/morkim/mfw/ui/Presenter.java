package lib.morkim.mfw.ui;

import android.content.Context;

public abstract class Presenter<C extends Controller> {

	protected Context context;
	protected C controller;

	public Presenter() {

	}

	public void onAttachController(C controller) {
		this.controller = controller;
		this.context = controller.getContext();

		onControllerAttached();
	}

	protected void onControllerAttached() {

	}
}