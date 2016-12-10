package lib.morkim.mfw.ui;

import android.app.Service;
import android.content.Context;
import android.os.Bundle;

import java.util.UUID;

import lib.morkim.mfw.app.MorkimApp;

public abstract class MorkimService<C extends Controller>
		extends Service
		implements Viewable<UpdateListener, C, EmptyPresenter> {

	protected C controller;
	protected EmptyPresenter presenter;

	@Override
	public void onCreate() {
		super.onCreate();

		UiComponentHelper.createUiComponents(this, getApplication());
	}

	@Override
	public void onBindViews() {

	}

	@Override
	public void onAttachController(C controller) {
		this.controller = controller;
	}

	@Override
	public void onAttachPresenter(EmptyPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public Bundle getBundledData() {
		return null;//getIntent().getExtras();
	}

	protected int layoutId() {
		return 0;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		((MorkimApp) getApplication()).destroyController(this);
	}

	@Override
	public void keepScreenOn(boolean keepOn) {

	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public void runOnUi(Runnable runnable) {}

	@Override
	public UUID getInstanceId() {
		return null;
	}

	@Override
	public void registerPermissionListener(String permission, onPermissionResultListener listener) {}

	@Override
	public <T> T getParentListener() {
		return null;
	}

	@Override
	public <T> T getChildListener() {
		return (T) controller;
	}
}
