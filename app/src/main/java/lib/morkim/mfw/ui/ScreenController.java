package lib.morkim.mfw.ui;

import android.app.Activity;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;

public abstract class ScreenController<A extends MorkimApp<M, ?>, M extends Model, VA extends UpdateListener>
		extends Controller<A, M, VA> {

	protected Activity activity;

	public ScreenController(A morkimApp) {
		super(morkimApp);
	}

	@Override
	public void attachViewable(Viewable<A, M, VA, ?, ?> viewable) {
		super.attachViewable(viewable);

		this.activity = (Activity) viewable;
	}

	@Override
	protected void finish() {
		activity.finish();
	}

	@Override
	protected A createContext() {
		//noinspection unchecked
		return (A) activity.getApplication();
	}

	@Override
	public Activity getActivity() {
		return (Activity) viewable;
	}
}
