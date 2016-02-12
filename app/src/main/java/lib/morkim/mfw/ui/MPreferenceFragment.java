package lib.morkim.mfw.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;

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

		controller = morkimApp.createController(this);
		presenter = morkimApp.createPresenter(this);
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
	public void notifyOnUiThread(Runnable runnable) {
		getActivity().runOnUiThread(runnable);
	}
	
	@Override
	public void setNavigation(Navigation navigation) {
		this.navigation = navigation;
	}



	protected abstract Controller createController();
}
