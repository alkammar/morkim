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

		presenter = morkimApp.acquirePresenter(this);
		controller = morkimApp.acquireController(this);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();

		controller.addObserver(this);
	}

	@Override
	public void onStop() {
		super.onStop();

		controller.deleteObserver(this);
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

	@Override
	public Screen getScreen() {
		return (Screen) getActivity();
	}

	@Override
	public Presenter getPresenter() {
		return presenter;
	}

	@Override
	public Controller getController() {
		return controller;
	}

	@Override
	public void showShortMessage(String message) {
		((Screen) getActivity()).showShortMessage(message);
	}
}
