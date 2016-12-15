package lib.morkim.mfw.usecase;


public abstract class OnTaskUpdateListener<Res extends TaskResult> implements UseCaseListener<Res> {

	@Override
	public void onTaskStart(UseCase useCase) {

	}

	@Override
	public void onTaskCancel() {

	}
}
