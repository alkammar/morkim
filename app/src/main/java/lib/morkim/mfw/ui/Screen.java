package lib.morkim.mfw.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import lib.morkim.mfw.R;
import lib.morkim.mfw.app.MorkimApp;

public abstract class Screen extends Activity implements Viewable {

	public static final String KEY_SCREEN_TRANSITION = "screen.transition";
	
	private static final String TAG_CONTROLLER_FRAGMENT = "controller.fragment.tag";

	protected Navigation navigation;
	protected Controller controller;
	protected Presenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setCustomTilteBar();

		int layoutId = layoutId();
		if (layoutId > 0)
			setContentView(layoutId);

		controller = ((MorkimApp) getApplicationContext()).createController(this);
		presenter = ((MorkimApp) getApplicationContext()).createPresenter(this);

		int transitionOrdinal = getIntent().getIntExtra(KEY_SCREEN_TRANSITION, Transition.NONE.ordinal());
		Transition transition = Transition.values()[transitionOrdinal];
		animateTransition(transition);
	}
	
	protected abstract Controller createController();
	protected abstract Presenter createPresenter();

	protected int layoutId() {
		return 0;
	}

	private void animateTransition(Transition transition) {
		switch (transition) {
		case RIGHT:
			overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
			break;
		case LEFT:
			overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();

		keepScreenOn(false);
	}

	protected void setCustomTilteBar() {

	}

	void keepScreenOn(boolean keepOn) {

		if (keepOn) 
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		else
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	public void notifyOnUiThread(Runnable runnable) {
		this.runOnUiThread(runnable);
	}

	@Override
	public void setNavigation(Navigation navigation) {
		this.navigation = navigation;
	}

	@Override
	public String getStringResource(int resource) {
		return getString(resource);
	}
	
	public Controller getController() {
		return controller;
	}

	@Override
	public Context getContext() {
		return this;
	}
}
