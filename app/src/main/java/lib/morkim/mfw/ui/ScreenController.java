package lib.morkim.mfw.ui;

import android.app.Activity;
import android.view.View;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;

public abstract class ScreenController<P extends Presenter, M extends Model, A extends MorkimApp<M, ?>>
		extends Controller<P, M, A> {

	protected Activity activity;

	public ScreenController(Viewable<M, A, ?, P> viewable) {
		super(viewable);
	}

	@Override
	public void setViewable(Viewable<M, A, ?, P> viewable) {
		super.setViewable(viewable);

		this.activity = (Activity) viewable;
	}

	@Override
	public View getViewById(int id) {
		return activity.findViewById(id);
	}
}
