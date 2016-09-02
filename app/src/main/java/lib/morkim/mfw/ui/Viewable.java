package lib.morkim.mfw.ui;

import android.content.Context;

import java.util.UUID;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;

public interface Viewable<A extends MorkimApp<M, ?>, M extends Model, V extends ViewableActions, C extends Controller, P extends Presenter> {

	static final String VIEWABLE_ID = "viewable.position";

	A getMorkimContext();
	Context getContext();

	V createActions();
	C createController();
	P createPresenter();

	V getActions();
	C getController();

	void keepScreenOn(boolean keepOn);

	void showShortMessage(String message);

	UUID getInstanceId();

	void attachController(C controller);

	void onBindViews();

}
