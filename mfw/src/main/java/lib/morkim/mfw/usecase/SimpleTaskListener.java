package lib.morkim.mfw.usecase;


public class SimpleTaskListener<Res extends TaskResult> implements UseCaseListener<Res> {

	@Override
	public void onTaskStart(UseCase useCase) {

	}

	@Override
	public void onTaskUpdate(Res res) {

	}

	@Override
	public void onTaskComplete(Res res) {

	}

	@Override
	public void onUndone(Res result) {

	}

	@Override
	public void onTaskCancel() {

	}
}
