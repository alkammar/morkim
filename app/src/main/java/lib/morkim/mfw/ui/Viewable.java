package lib.morkim.mfw.ui;

import android.content.Context;

import lib.morkim.mfw.app.AppContext;

public interface Viewable {

	public void finish();
	AppContext getMorkimContext();

	void keepScreenOn(boolean keepOn);

	Context getContext();
}
