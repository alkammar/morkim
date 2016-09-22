package lib.morkim.mfw.ui;

import android.content.Context;
import android.os.Bundle;

import java.util.UUID;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;

public interface Viewable<A extends MorkimApp<M, ?>, M extends Model, V extends UpdateListener, C extends Controller, P extends Presenter>
		extends UpdateListener {

	static final String VIEWABLE_ID = "viewable.position";

	A getMorkimContext();
	Context getContext();

	V getUpdateListener();
	C getController();

	void keepScreenOn(boolean keepOn);

	UUID getInstanceId();

	void onBindViews();

	void registerPermissionListener(String permission, onPermissionResultListener listener);

	Bundle getBundledData();

	void onAttachController(C controller);

	void onAttachPresenter(P presenter);
}
