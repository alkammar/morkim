package lib.morkim.mfw.ui;

import lib.morkim.mfw.usecase.UseCase;
import lib.morkim.mfw.usecase.UseCaseResult;
import lib.morkim.mfw.usecase.UseCaseProgress;

public class EmptyPresenter extends Presenter {

	public EmptyPresenter(Viewable viewable) {
		super(viewable);
	}

	@Override
	public void onUseCaseStart(UseCase useCase) {}

	@Override
	public void onUseCaseUpdate(UseCaseProgress response) {}

	@Override
	public void onUseCaseComplete(UseCaseResult response) {}

	@Override
	public void onUseCaseCancel() {}

}
