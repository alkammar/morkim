package lib.morkim.mfw.usecase;


public interface MorkimTaskListener<Res extends TaskResult> {

	public void onTaskStart(MorkimTask useCase);
	public void onTaskUpdate(Res result);
	public void onTaskComplete(Res result);
	public void onTaskCancel();
}
