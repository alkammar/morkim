package lib.morkim.examples;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import lib.morkim.mfw.ui.EmptyPresenter;
import lib.morkim.mfw.ui.MorkimFragment;
import lib.morkim.mfw.ui.UpdateListener;


public class ExampleFragment extends MorkimFragment<UpdateListener, ExampleFragmentController, EmptyPresenter> {

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
	public void onBindViews() {

		getView().findViewById(R.id.button).setOnClickListener(controller.buttonClickListener);
	}
}
