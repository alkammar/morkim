package lib.morkim.examples.screen.childfragment;

import lib.morkim.examples.R;
import lib.morkim.mfw.ui.EmptyPresenter;
import lib.morkim.mfw.ui.MorkimFragment;
import lib.morkim.mfw.ui.UpdateListener;


public class ExampleChildFragment extends MorkimFragment<UpdateListener, ExampleChildFragmentController, EmptyPresenter> {

	@Override
	protected int layoutId() {
		return R.layout.fragment_child_example;
	}

	@Override
	public void onAssignListeners() {

	}
}
