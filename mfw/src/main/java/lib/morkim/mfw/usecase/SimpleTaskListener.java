package lib.morkim.mfw.usecase;


public class SimpleTaskListener<Res extends UseCaseResult> implements UseCaseListener<Res> {

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

	@Override
	public boolean onTaskError(Res errorResult) {
		return false;
	}

	@Override
	public void onTaskAborted() {

	}
}
