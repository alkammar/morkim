package lib.morkim.examples;

import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.ui.ScreenController;
import lib.morkim.mfw.ui.UpdateListener;

class ExampleBaseController<V extends UpdateListener> extends ScreenController<ExampleApp, Model, V> {

	ExampleBaseController(ExampleApp morkimApp) {
		super(morkimApp);
	}
}
