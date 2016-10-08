package lib.morkim.examples;

import android.view.View;

import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.ui.Controller;
import lib.morkim.mfw.ui.UpdateListener;
import lib.morkim.mfw.ui.Viewable;

class ExampleFragmentController extends Controller<ExampleApp, Model, UpdateListener> {

	private ExampleParentListener parentListener;

	public ExampleFragmentController(ExampleApp morkimApp) {
		super(morkimApp);
	}

	@Override
	public void onAttachViewable(Viewable<UpdateListener, ?, ?> viewable) {
		super.onAttachViewable(viewable);

		parentListener = viewable.getParentListener();
	}

	View.OnClickListener buttonClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			parentListener.onDoSomethingWhenButtonClicked();
		}
	};
}
