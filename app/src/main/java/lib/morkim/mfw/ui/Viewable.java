package lib.morkim.mfw.ui;

import android.content.Context;

import java.util.Observer;

import lib.morkim.mfw.app.AppContext;

public interface Viewable extends Observer {

	Context getContext();
	Controller getController();

	public void finish();
	AppContext getMorkimContext();

	void keepScreenOn(boolean keepOn);
}
