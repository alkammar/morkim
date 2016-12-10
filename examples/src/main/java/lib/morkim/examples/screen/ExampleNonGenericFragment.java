package lib.morkim.examples.screen;

import lib.morkim.examples.R;
import lib.morkim.mfw.ui.MorkimFragment;

/**
 * Created by Kammar on 12/10/2016.
 */
public class ExampleNonGenericFragment extends MorkimFragment {

	@Override
	protected int layoutId() {
		return R.layout.fragment_example;
	}

	@Override
	public void onAssignListeners() {

	}
}
