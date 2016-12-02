package lib.morkim.mfw.usecase;


public class SimpleTaskListener<Res extends TaskResult> implements MorkimTaskListener<Res> {

	@Override
	public void onTaskStart(MorkimTask morkimTask) {

	}

	@Override
	public void onTaskUpdate(Res res) {

	}

	@Override
	public void onTaskComplete(Res res) {

	}

	@Override
	public void onTaskCancel() {

	}
}
