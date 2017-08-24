package lib.morkim.mfw.usecase;

public class EmptyUseCase extends UseCase {

	@Override
	protected UseCaseResult onExecute(UseCaseRequest request) {
		return new UseCaseResult() {};
	}

	@Override
	protected UseCaseResult onUndo(UseCaseRequest request) {
		return new UseCaseResult() {};
	}
}
