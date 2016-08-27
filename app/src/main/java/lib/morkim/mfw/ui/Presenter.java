package lib.morkim.mfw.ui;

import android.content.Context;

public abstract class Presenter<C extends Controller> {

	protected Context context;
	protected C controller;

	public Presenter(Context context) {
		this.context = context;
	}

	void setController(C controller) {
		this.controller = controller;
	}
}