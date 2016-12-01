package lib.morkim.examples;

import lib.morkim.examples.app.ExampleApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.ui.Controller;
import lib.morkim.mfw.ui.UpdateListener;

public class ExampleBaseController<V extends UpdateListener> extends Controller<ExampleApp, Model, V> {

	protected ExampleBaseController(ExampleApp morkimApp) {
		super(morkimApp);
	}
}
