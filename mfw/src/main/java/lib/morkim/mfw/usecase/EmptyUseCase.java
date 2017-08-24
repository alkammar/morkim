package lib.morkim.mfw.usecase;

public class EmptyUseCase extends UseCase {

	@Override
	protected void onExecute(UseCaseRequest request) {

	}

	@Override
	protected UseCaseResult onUndo(UseCaseRequest request) {
		return new UseCaseResult() {};
	}
}
