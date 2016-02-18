package lib.morkim.mfw.ui;

import android.content.Context;

import java.util.Observer;
import java.util.UUID;

import lib.morkim.mfw.app.AppContext;

public interface Viewable<C, P> extends Observer {

	static final String VIEWABLE_ID = "viewable.id";

	Context getContext();

	Screen getScreen();
	C getController();
	P getPresenter();

	public void finish();
	AppContext getMorkimContext();

	void keepScreenOn(boolean keepOn);

	void showShortMessage(String message);

	UUID getInstanceId();
}
