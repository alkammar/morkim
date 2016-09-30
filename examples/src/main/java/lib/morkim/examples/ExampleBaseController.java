package lib.morkim.examples;

import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.ui.Controller;
import lib.morkim.mfw.ui.UpdateListener;

class ExampleBaseController<V extends UpdateListener> extends Controller<ExampleApp, Model, V> {

	ExampleBaseController(ExampleApp morkimApp) {
		super(morkimApp);
	}
}
