package lib.morkim.mfw.ui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import java.util.UUID;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.repo.MorkimRepository;

public abstract class MorkimDialogFragment<A extends MorkimApp<M, MorkimRepository>, M extends Model, V extends DialogUpdateListener, C extends Controller, P extends Presenter>
		extends DialogFragment
		implements Viewable<A, M, V, C, P>, DialogUpdateListener {

	private UUID id;

	protected C controller;
	protected P presenter;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		id = (savedInstanceState == null) ? UUID.randomUUID() : UUID.fromString(savedInstanceState.getString(VIEWABLE_ID));

		Dialog dialog = createDialog();

		int layoutId = layoutId();
		if (layoutId > 0)
			dialog.setContentView(layoutId());

		getMorkimContext().createFrameworkComponents(this);

		return dialog;
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
	public A getMorkimContext() {
		//noinspection unchecked
		return (A) getActivity().getApplication();
	}

	@Override
	public Context getContext() {
		return getActivity();
	}

	@Override
	public C getController() {
		return controller;
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
	public void keepScreenOn(boolean b) {

	}

	@Override
	public UUID getInstanceId() {
		return id;
	}

	@Override
	public void registerPermissionListener(String s, onPermissionResultListener onPermissionResultListener) {

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putString(VIEWABLE_ID, id.toString());
	}

	protected int layoutId() {
		return 0;
	}
}
