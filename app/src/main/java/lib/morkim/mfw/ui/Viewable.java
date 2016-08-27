package lib.morkim.mfw.ui;

import android.content.Context;

import java.util.UUID;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;

public interface Viewable<M extends Model, A extends MorkimApp<M, ?>, C extends Controller, P extends Presenter> {

	static final String VIEWABLE_ID = "viewable.position";

	Context getContext();

	C createController();
	P createPresenter();

	Screen<C, P> getScreen();
	C getController();
	P getPresenter();

	void finish();
	A getMorkimContext();

	void keepScreenOn(boolean keepOn);

	void showShortMessage(String message);

	UUID getInstanceId();

	void attachController(C controller);

	void onBindViews();
}
