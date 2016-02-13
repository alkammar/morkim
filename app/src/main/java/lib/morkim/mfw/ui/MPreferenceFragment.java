package lib.morkim.mfw.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.app.MorkimApp;

@SuppressLint("NewApi")
public abstract class MPreferenceFragment extends PreferenceFragment implements Viewable {
	
	private static final String TAG_CONTROLLER_FRAGMENT = "fragment.controller.fragment.tag";

	protected Navigation navigation;

	protected Controller controller;
	private Presenter presenter;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	
		MorkimApp morkimApp = (MorkimApp) activity.getApplication();
		
		navigation = morkimApp.acquireNavigation();

		controller = morkimApp.acquireController(this);
		presenter = morkimApp.acquirePresenter(this);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public AppContext getMorkimContext() {
		return ((Screen) getActivity()).getMorkimContext();
	}

	@Override
	public void keepScreenOn(boolean keepOn) {
		((Screen) getActivity()).keepScreenOn(keepOn);
	}

	@Override
	public void finish() {

	}

	protected Presenter getPresenter() {
		return presenter;
	}
}
