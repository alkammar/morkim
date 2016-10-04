package lib.morkim.mfw.ui;

import android.content.Context;
import android.os.Bundle;

import java.util.UUID;

public interface Viewable<V extends UpdateListener, C extends Controller, P extends Presenter>
		extends UpdateListener {

	static final String VIEWABLE_ID = "viewable.position";

	Context getContext();

	void runOnUi(Runnable runnable);
	void finish();

	V getUpdateListener();

	<T> T getParentListener();
	<T> T getChildListener();

	void keepScreenOn(boolean keepOn);

	UUID getInstanceId();

	void onBindViews();

	void registerPermissionListener(String permission, onPermissionResultListener listener);

	Bundle getBundledData();

	void onAttachController(C controller);

	void onAttachPresenter(P presenter);
}
