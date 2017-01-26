package lib.morkim.mfw.ui;

import android.app.Service;
import android.content.Context;
import android.os.Bundle;

import java.util.UUID;

import lib.morkim.mfw.app.MorkimApp;

public abstract class MorkimService<U extends UpdateListener, C extends Controller>
		extends Service
		implements Viewable<U, C, EmptyPresenter> {

	private UUID id;

	protected C controller;
	protected EmptyPresenter presenter;

	@Override
	public void onCreate() {
		super.onCreate();

		id = UUID.randomUUID();

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
	public void finish() {
		stopSelf();
	}

	@Override
	public U getUpdateListener() {
		return (U) this;
	}

	@Override
	public void onAssignListeners() {}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public void runOnUi(Runnable runnable) {}

	@Override
	public UUID getInstanceId() {
		return id;
	}

	@Override
	public void registerPermissionListener(String permission, OnPermissionResultListener1 listener) {}

	@Override
	public <T> T getParentListener() {
		return null;
	}

	@Override
	public <T> T getChildListener() {
		return (T) controller;
	}
}
