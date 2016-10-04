package lib.morkim.examples;

import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.ui.Controller;
import lib.morkim.mfw.ui.UpdateListener;

class ExampleFragmentController extends Controller<ExampleApp, Model, UpdateListener> {

	private final ExampleParentListener listener;

	public ExampleFragmentController(ExampleApp morkimApp) {
		super(morkimApp);

		listener = viewable.getParentListener();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		listener.onSomethingHappenedInChild();
	}
}
