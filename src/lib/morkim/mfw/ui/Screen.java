package lib.morkim.mfw.ui;

import java.util.Observer;

import lib.morkim.mfw.R;
import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.usecase.UseCaseStateListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public abstract class Screen extends Activity implements Viewable {

	public static final String KEY_SCREEN_TRANSITION = "screen.transition";

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

		navigation = ((MorkimApp) getApplication()).acquireNavigation();
		presenter = ((MorkimApp) getApplication()).createPresenter(this);
		controller = ((MorkimApp) getApplication()).acquireController(this);

		controller.attach(this, savedInstanceState);

		int transitionOrdinal = getIntent().getIntExtra(KEY_SCREEN_TRANSITION, Transition.NONE.ordinal());
		Transition transition = Transition.values()[transitionOrdinal];
		animateTransition(transition);
	}

	protected int layoutId() {
		return 0;
	}

	public void configureUiElements() {};

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

		keepScreenOn(keepScreenOn());

		presenter.bindViewModel();
	}

	@Override
	protected void onPause() {
		super.onPause();

		presenter.unbindViewModel();

		keepScreenOn(false);
	}

	protected void setCustomTilteBar() {

	}

	private void keepScreenOn(boolean keepOn) {

		if (keepOn) 
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		else
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		((MorkimApp) getApplication()).destroyController(controller);
	}

	@Override
	public void finish() {
		super.finish();

		((MorkimApp) getApplication()).destroyController(controller);
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

	@Override
	public UseCaseStateListener getUseCaseListener() {
		return presenter;
	}
	
	@Override
	public Observer getTaskListener() {
		return presenter;
	}

	protected boolean keepScreenOn() {
		return false;
	}
}
