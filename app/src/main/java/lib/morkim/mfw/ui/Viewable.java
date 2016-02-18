package lib.morkim.mfw.ui;

import android.content.Context;

import java.util.Observer;
import java.util.UUID;

import lib.morkim.mfw.app.AppContext;

public interface Viewable extends Observer {

	static final String VIEWABLE_ID = "viewable.id";

	Context getContext();

	Screen getScreen();
	Controller getController();
	Presenter getPresenter();

	public void finish();
	AppContext getMorkimContext();

	void keepScreenOn(boolean keepOn);

	void showShortMessage(String message);

	UUID getInstanceId();
}
