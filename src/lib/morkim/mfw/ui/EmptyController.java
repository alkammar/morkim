package lib.morkim.mfw.ui;

import java.util.HashMap;
import java.util.Map;

import lib.morkim.mfw.app.AppContext;

public class EmptyController extends Controller {

	public EmptyController(AppContext appContext, Viewable viewable) {
		super(appContext, viewable);
	}

	@Override
	protected Map<String, ViewListener> getListeners() {
		return new HashMap<String, ViewListener>();
	}

}
