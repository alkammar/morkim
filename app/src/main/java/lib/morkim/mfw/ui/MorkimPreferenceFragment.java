package lib.morkim.mfw.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import java.util.UUID;

@SuppressLint("NewApi")
public abstract class MorkimPreferenceFragment<V extends UpdateListener, C extends Controller, P extends Presenter>
		extends PreferenceFragment
		implements Viewable<V, C, P> {

	protected C controller;
	protected P presenter;

	private UUID id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		id = (savedInstanceState == null) ? UUID.randomUUID() : UUID.fromString(savedInstanceState.getString(VIEWABLE_ID));

		UiComponentHelper.createUiComponents(this, getActivity());
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
		return getArguments();
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
	public Context getContext() {
		return getActivity();
	}

	@Override
	public void runOnUi(Runnable runnable) {
		getActivity().runOnUiThread(runnable);
	}

	@Override
	public void finish() {
		getFragmentManager()
				.beginTransaction()
				.remove(this)
				.commit();
	}

	@Override
	public void keepScreenOn(boolean keepOn) {
		((AppCompatScreen) getActivity()).keepScreenOn(keepOn);
	}

	@Override
	public UUID getInstanceId() {
		return id;
	}

	@Override
	public <T> T getParentListener() {
		return UiComponentHelper.getParentAsListener(this);
	}
}
