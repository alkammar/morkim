package lib.morkim.mfw.usecase;


public interface MorkimTaskListener<Res extends TaskResult> {

	void onTaskStart(MorkimTask useCase);
	void onTaskUpdate(Res result);
	void onTaskComplete(Res result);
	void onTaskCancel();
}
