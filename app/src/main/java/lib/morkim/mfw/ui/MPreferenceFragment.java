package lib.morkim.mfw.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import java.util.UUID;

import lib.morkim.mfw.app.MorkimApp;

@SuppressLint("NewApi")
public abstract class MPreferenceFragment<C extends Controller, P extends Presenter> extends PreferenceFragment implements Viewable<MorkimApp, C, P> {

	protected C controller;
	private P presenter;
	private UUID id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		id = (savedInstanceState == null) ? UUID.randomUUID() : UUID.fromString(savedInstanceState.getString(VIEWABLE_ID));

		presenter = createPresenter();
		controller = (C) getMorkimContext().acquireController(this);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putString(VIEWABLE_ID, id.toString());
	}

	@Override
	public void onStart() {
		super.onStart();

		controller.bindViews();
	}

	@Override
	public void onStop() {
		super.onStop();

		controller.unbindViews();
	}

	@Override
	public MorkimApp getMorkimContext() {
		return (MorkimApp) getActivity().getApplicationContext();
	}

	@Override
	public Context getContext() {
		return getActivity();
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
	public P getPresenter() {
		return presenter;
	}

	@Override
	public C getController() {
		return controller;
	}

	@Override
	public void showShortMessage(String message) {
		((Viewable) getActivity()).showShortMessage(message);
	}

	@Override
	public UUID getInstanceId() {
		return id;
	}
}
