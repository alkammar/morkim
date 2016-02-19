package lib.morkim.mfw.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.UUID;

import lib.morkim.mfw.R;
import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.app.MorkimApp;

public abstract class Screen<C extends Controller, P extends Presenter> extends AppCompatActivity implements Viewable<C, P> {

	public static final String KEY_SCREEN_TRANSITION = "screen.transition";

	private UUID id;

	protected C controller;
	protected P presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		id = (savedInstanceState == null) ? UUID.randomUUID() : UUID.fromString(savedInstanceState.getString(VIEWABLE_ID));

		setCustomTilteBar();

		int layoutId = layoutId();
		if (layoutId > 0)
			setContentView(layoutId);

		presenter = (P) ((MorkimApp) getMorkimContext()).acquirePresenter(this);
		controller = (C) ((MorkimApp) getMorkimContext()).acquireController(this);

		int transitionOrdinal = getIntent().getIntExtra(KEY_SCREEN_TRANSITION, Transition.NONE.ordinal());
		Transition transition = Transition.values()[transitionOrdinal];
		animateTransition(transition);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putString(VIEWABLE_ID, id.toString());
	}

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
	protected void onStart() {
		super.onStart();

		controller.addObserver(this);
	}

	@Override
	protected void onPause() {
		super.onPause();

		keepScreenOn(false);
	}

	@Override
	protected void onStop() {
		super.onStop();

		controller.deleteObserver(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (isFinishing()) {
			((MorkimApp) getApplication()).destroyController(this);
			((MorkimApp) getApplication()).destroyPresenter(this);
		}
	}

	protected void setCustomTilteBar() {

	}

	@Override
	public void keepScreenOn(boolean keepOn) {

		if (keepOn) 
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		else
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	public AppContext getMorkimContext() {
		return (AppContext) getApplication();
	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public Screen getScreen() {
		return this;
	}

	@Override
	public C getController() {
		return controller;
	}

	@Override
	public P getPresenter() {
		return presenter;
	}

	@Override
	public void showShortMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public UUID getInstanceId() {
		return id;
	}
}
