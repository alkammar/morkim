package lib.morkim.mfw.usecase;


public abstract class OnTaskUpdateListener<Res extends TaskResult> implements MorkimTaskListener<Res> {

	@Override
	public void onTaskStart(MorkimTask useCase) {

	}

	@Override
	public void onTaskCancel() {

	}
}
