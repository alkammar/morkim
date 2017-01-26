package lib.morkim.mfw.ui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.UUID;

public abstract class MorkimDialogFragment<V extends DialogUpdateListener, C extends Controller, P extends Presenter>
		extends DialogFragment
		implements Viewable<V, C, P>, DialogUpdateListener {

	private UUID id;

	protected C controller;
	protected P presenter;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		Dialog dialog = createDialog();

		int layoutId = layoutId();
		if (layoutId > 0)
			dialog.setContentView(layoutId());

		return dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		id = (savedInstanceState == null) ? UUID.randomUUID() : UUID.fromString(savedInstanceState.getString(VIEWABLE_ID));

		UiComponentHelper.createUiComponents(this, getActivity().getApplication());

		controller.onAttachParent(this);

		return super.onCreateView(inflater, container, savedInstanceState);
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
		return getArguments();
	}

	protected abstract Dialog createDialog();

	@Override
	public void dismissDialog() {
		getDialog().dismiss();
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
	public V getUpdateListener() {
		return (V) this;
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
	public UUID getInstanceId() {
		return id;
	}

	@Override
	public void registerPermissionListener(String s, OnPermissionResultListener1 onPermissionResultListener) {

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putString(VIEWABLE_ID, id.toString());
	}

	protected int layoutId() {
		return 0;
	}

	@Override
	public <T> T getParentListener() {
		return UiComponentHelper.getParentAsListener(this);
	}

	@Override
	public <T> T getChildListener() {
		return (T) controller;
	}

	@Override
	public void finish() {
		dismissDialog();
	}
}
