package lib.morkim.mfw.ui;

import android.app.Activity;
import android.view.View;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;

public abstract class ScreenController<A extends MorkimApp<M, ?>, M extends Model, VA extends UpdateListener>
		extends Controller<A, M, VA> {

	protected Activity activity;

	public ScreenController(A morkimApp) {
		super(morkimApp);
	}

	@Override
	public void setViewable(Viewable<A, M, VA, ?, ?> viewable) {
		super.setViewable(viewable);

		this.activity = (Activity) viewable;
	}

	@Override
	public View getViewById(int id) {
		return activity.findViewById(id);
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
}
