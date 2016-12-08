package lib.morkim.examples.screen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import lib.morkim.examples.R;
import lib.morkim.examples.screen.childfragment.ExampleChildFragment;
import lib.morkim.mfw.ui.EmptyPresenter;
import lib.morkim.mfw.ui.MorkimFragment;


public class ExampleFragment
		extends MorkimFragment<ExampleFragmentUpdateListener, ExampleFragmentController, EmptyPresenter>
		implements ExampleFragmentUpdateListener {

	@Override
	protected int layoutId() {
		return R.layout.fragment_example;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		getFragmentManager()
				.beginTransaction()
				.replace(R.id.fl_child_fragment, new ExampleChildFragment())
				.commit();
	}

	@Override
	public void onAssignListeners() {

		getView().findViewById(R.id.button).setOnClickListener(controller.buttonClickListener);
	}

	@Override
	public void doFragmentAction() {
		getView().findViewById(R.id.button).setBackgroundColor(0xff0000ff);
	}
}
