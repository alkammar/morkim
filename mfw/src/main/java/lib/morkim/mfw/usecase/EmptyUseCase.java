package lib.morkim.mfw.usecase;

public class EmptyUseCase extends UseCase {

	@Override
	protected TaskResult onExecute(TaskRequest request) {
		return new TaskResult() {};
	}

	@Override
	protected TaskResult onUndo(TaskRequest request) {
		return new TaskResult() {};
	}
}
