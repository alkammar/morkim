package lib.morkim.mfw.ui;

import android.app.Activity;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;

public class EmptyController extends Controller<MorkimApp<Model, ?>, Model, UpdateListener> {

	public EmptyController(MorkimApp<Model, ?> morkimApp) {
		super(morkimApp);
	}

	@Override
	protected MorkimApp<Model, ?> createContext() {
		return null;
	}

	@Override
	public Activity getActivity() {
		return null;
	}
}
