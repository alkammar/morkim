package lib.morkim.examples.screen;

import lib.morkim.examples.ExampleBaseController;
import lib.morkim.examples.R;
import lib.morkim.mfw.ui.MorkimFragment;
import lib.morkim.mfw.ui.Presenter;
import lib.morkim.mfw.ui.UpdateListener;

/**
 * Created by Kammar on 12/10/2016.
 */
public class ExampleNonAbstractGenericFragment<U extends UpdateListener, C extends ExampleBaseController, P extends Presenter>
		extends MorkimFragment<U, C, P> {

	@Override
	protected int layoutId() {
		return R.layout.fragment_example;
	}

	@Override
	public void onAssignListeners() {

	}
}
