package lib.morkim.mfw.adapters;

import java.util.HashMap;
import java.util.Map;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.ui.MView;
import lib.morkim.mfw.ui.ViewListener;
import lib.morkim.mfw.ui.ViewModel;

public class EmptyController extends Controller {

	public EmptyController(AppContext appContext, MView view) {
		super(appContext, view);
	}

	@Override
	protected void buildInitializationModel(ViewModel viewModel) {}

	@Override
	protected Map<String, ViewListener> getListeners() {
		return new HashMap<String, ViewListener>();
	}

}
