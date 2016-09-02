package lib.morkim.mfw.ui;

import lib.morkim.mfw.app.MorkimApp;

public class EmptyController extends ScreenController {

	public EmptyController(MorkimApp morkimApp) {
		super(morkimApp);
	}

	@Override
	protected ViewableActions createEmptyViewableActions() {
		return null;
	}
}
