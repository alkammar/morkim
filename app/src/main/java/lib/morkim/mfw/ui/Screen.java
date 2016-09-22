package lib.morkim.mfw.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import lib.morkim.mfw.R;
import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;

public abstract class Screen<A extends MorkimApp<M, ?>, M extends Model, V extends UpdateListener, C extends Controller, P extends Presenter>
		extends AppCompatActivity
		implements Viewable<A, M, V, C, P> {

	public static final String KEY_SCREEN_TRANSITION = "screen.transition";

	private UUID id;

	protected C controller;
	protected P presenter;

	private Map<String, onPermissionResultListener> permissionsRequestControllers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		id = (savedInstanceState == null) ? UUID.randomUUID() : UUID.fromString(savedInstanceState.getString(VIEWABLE_ID));

		setCustomTitleBar();

		int layoutId = layoutId();
		if (layoutId > 0)
			setContentView(layoutId);

		permissionsRequestControllers = new HashMap<>();

		getMorkimContext().createFrameworkComponents(this);

		int transitionOrdinal = getIntent().getIntExtra(KEY_SCREEN_TRANSITION, Transition.NONE.ordinal());
		Transition transition = Transition.values()[transitionOrdinal];
		animateTransition(transition);
	}

	@Override
	public void onAttachController(C controller) {
		this.controller = controller;
	}

	@Override
	public void onAttachPresenter(P presenter) {
		this.presenter = presenter;
	}

	@Override
	public Bundle getBundledData() {
		return getIntent().getExtras();
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

		controller.bindViews();
	}

	@Override
	protected void onPause() {
		super.onPause();

		keepScreenOn(false);
	}

	@Override
	protected void onStop() {
		super.onStop();

        controller.unbindViews();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (isFinishing())
			((MorkimApp) getApplication()).destroyController(this);
	}

	protected void setCustomTitleBar() {

	}

	@Override
	public void keepScreenOn(boolean keepOn) {

		if (keepOn) getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		else getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	public A getMorkimContext() {
		//noinspection unchecked
		return ((A) getApplication());
	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public C getController() {
		return controller;
	}

	@Override
	public UUID getInstanceId() {
		return id;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		Set<onPermissionResultListener> listeners = new HashSet<>();

		for (String permission : permissions) {
			onPermissionResultListener controller = permissionsRequestControllers.get(permission);
			if (controller != null)
				listeners.add(controller);
		}

		for (onPermissionResultListener listener : listeners)
			listener.onRequestPermissionResult(requestCode, permissions, grantResults);
	}

	@Override
	public void registerPermissionListener(String permission, onPermissionResultListener listener) {
		permissionsRequestControllers.put(permission, listener);
	}

	public void onPermissionRequestHandled(String permission) {
		permissionsRequestControllers.remove(permission);
	}
}
