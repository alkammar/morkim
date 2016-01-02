package lib.morkim.mfw.ui;

import lib.morkim.mfw.usecase.UseCaseResult;

public class EmptyPresenter extends Presenter {

	public EmptyPresenter(Viewable viewable) {
		super(viewable);
	}

	@Override
	public void onUseCaseComplete(UseCaseResult response) {}

	@Override
	protected void initializeViewModel(ViewModel viewModel) {}

}
