package lib.morkim.mfw.ui;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;

public abstract class SupportFragmentController<A extends MorkimApp<M, ?>, M extends Model, VA extends UpdateListener>
		extends Controller<A, M, VA> {

	protected Fragment fragment;

	public SupportFragmentController(A morkimApp) {
		super(morkimApp);
	}

	@Override
	public void attachViewable(Viewable<A, M, VA, ?, ?> viewable) {
		super.attachViewable(viewable);

		this.fragment = (Fragment) viewable;
	}

	@Override
	public View getViewById(int id) {
		return fragment.getView().findViewById(id);
	}

	@Override
	protected void finish() {

	}

	@Override
	protected A createContext() {
		//noinspection unchecked
		return (A) fragment.getActivity().getApplication();
	}

	@Override
	public Activity getActivity() {
		return fragment.getActivity();
	}
}
