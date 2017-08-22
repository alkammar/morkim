package lib.morkim.examples.screen.childfragment;

import lib.morkim.examples.R;
import lib.morkim.mfw.ui.EmptyPresenter;
import lib.morkim.mfw.ui.MorkimFragment;


public class ExampleChildFragment extends MorkimFragment<ExampleChildFragmentController, EmptyPresenter> {

	@Override
	protected int layoutId() {
		return R.layout.fragment_child_example;
	}

	@Override
	public void onAssignListeners() {

	}
}
