package lib.morkim.mfw.ui;

import android.content.Context;

import java.util.UUID;

import lib.morkim.mfw.app.MorkimApp;

public interface Viewable<A extends MorkimApp, C extends Controller, P extends Presenter> {

	static final String VIEWABLE_ID = "viewable.position";

	Context getContext();

	C createController();
	P createPresenter();

	Screen<C, P> getScreen();
	C getController();
	P getPresenter();

	public void finish();
	A getMorkimContext();

	void keepScreenOn(boolean keepOn);

	void showShortMessage(String message);

	UUID getInstanceId();
}
