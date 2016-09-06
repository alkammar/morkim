package lib.morkim.mfw.ui;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;

public class EmptyScreenController extends ScreenController<MorkimApp<Model, ?>, Model, UpdateListener> {

	public EmptyScreenController() {
		super(null);
	}

	@Override
	protected Class<UpdateListener> getUpdateListenerClass() {
		return UpdateListener.class;
	}
}
