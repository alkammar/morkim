package lib.morkim.mfw.adapters;

import java.util.HashMap;
import java.util.Map;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.ui.Viewable;
import lib.morkim.mfw.ui.ViewListener;

public class EmptyController extends Controller {

	public EmptyController(AppContext appContext, Viewable viewable) {
		super(appContext, viewable);
	}

	@Override
	protected Map<String, ViewListener> getListeners() {
		return new HashMap<String, ViewListener>();
	}

}
