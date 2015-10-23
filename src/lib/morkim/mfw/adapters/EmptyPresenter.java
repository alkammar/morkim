package lib.morkim.mfw.adapters;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.ui.Viewable;
import lib.morkim.mfw.ui.ViewModel;
import lib.morkim.mfw.usecase.UseCaseResult;

public class EmptyPresenter extends Presenter {

	public EmptyPresenter(AppContext appContext, Viewable viewable) {
		super(appContext, viewable);
	}

	@Override
	public void onUseCaseComplete(UseCaseResult response) {}

	@Override
	protected void buildInitializationModel(ViewModel viewModel) {}

}
