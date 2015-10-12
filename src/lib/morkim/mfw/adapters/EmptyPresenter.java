package lib.morkim.mfw.adapters;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.ui.MView;
import lib.morkim.mfw.ui.ViewModel;
import lib.morkim.mfw.usecase.UseCaseResult;

public class EmptyPresenter extends Presenter {

	public EmptyPresenter(AppContext appContext, MView view) {
		super(appContext, view);
	}

	@Override
	public void onUseCaseComplete(UseCaseResult response) {}

	@Override
	protected void buildInitializationModel(ViewModel viewModel) {}

}
