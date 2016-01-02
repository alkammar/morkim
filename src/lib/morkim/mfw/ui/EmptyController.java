package lib.morkim.mfw.ui;

import java.util.HashMap;
import java.util.Map;

public class EmptyController extends Controller {

	@Override
	protected Map<String, ViewListener> addListeners(HashMap<String, ViewListener> listeners) {
		return null;
	}

	@Override
	protected Presenter createPresenter() {
		return new EmptyPresenter(viewable);
	}

}
