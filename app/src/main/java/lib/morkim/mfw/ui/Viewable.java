package lib.morkim.mfw.ui;

import android.content.Context;

import java.util.Observer;
import java.util.UUID;

import lib.morkim.mfw.app.MorkimApp;

public interface Viewable<C extends Controller, P extends Presenter> extends Observer {

	static final String VIEWABLE_ID = "viewable.id";

	Context getContext();

	C createController();
	P createPresenter();

	Screen<C, P> getScreen();
	C getController();
	P getPresenter();

	public void finish();
	MorkimApp getMorkimContext();

	void keepScreenOn(boolean keepOn);

	void showShortMessage(String message);

	UUID getInstanceId();
}
