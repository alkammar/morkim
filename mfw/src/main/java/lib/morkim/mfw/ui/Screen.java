package lib.morkim.mfw.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import lib.morkim.mfw.app.MorkimApp;

public abstract class Screen<V extends UpdateListener, C extends Controller, P extends Presenter>
		extends Activity
		implements Viewable<V, C, P> {

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

		UiComponentHelper.createUiComponents(this, this);
	}

	@Override
	public void onBindViews() {

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
	public Context getContext() {
		return this;
	}

	@Override
	public void runOnUi(Runnable runnable) {
		runOnUiThread(runnable);
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

	@Override
	public <T> T getParentListener() {
		return null;
	}

	@Override
	public <T> T getChildListener() {
		return (T) controller;
	}
}
