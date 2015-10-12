package lib.morkim.mfw.ui;

import lib.morkim.mfw.adapters.Controller;
import lib.morkim.mfw.adapters.Presenter;
import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.usecase.UseCaseStateListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;

@SuppressLint("NewApi")
public abstract class MPreferenceFragment extends PreferenceFragment implements MView {

	protected Navigation navigation;
	protected Controller controller;
	protected Presenter presenter;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	
		MorkimApp morkimApp = (MorkimApp) activity.getApplication();
		
		navigation = morkimApp.acquireNavigation();
		controller = morkimApp.acquireController(this);
		presenter = morkimApp.createPresenter(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		controller.attach(this, null);
	}
	
	@Override
	public void onResume() {
		super.onResume();

		presenter.bindViewModel();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		presenter.unbindViewModel();
	}
	
	@Override
	public void configureUiElements() {}
	
	@Override
	public void setNavigation(Navigation navigation) {
		this.navigation = navigation;
	}
	
	@Override
	public UseCaseStateListener getUseCaseListener() {
		return presenter;
	}
}
