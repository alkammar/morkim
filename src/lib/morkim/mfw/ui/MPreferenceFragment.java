package lib.morkim.mfw.ui;

import lib.morkim.mfw.adapters.Controller;
import lib.morkim.mfw.app.MorkimApp;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;

@SuppressLint("NewApi")
public abstract class MPreferenceFragment extends PreferenceFragment implements MView {

	protected Navigation navigation;
	protected Controller controller;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	
		MorkimApp mvcApp = (MorkimApp) activity.getApplication();
		
		navigation = mvcApp.acquireNavigation();
		controller = mvcApp.acquireController(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		controller.attach(this, null);
	}
	
	@Override
	public void onResume() {
		super.onResume();

		controller.bindViewModel();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		controller.unbindViewModel();
	}
	
	@Override
	public void configureUiElements() {}
	
	@Override
	public void setNavigation(Navigation navigation) {
		this.navigation = navigation;
	}
}
