package lib.morkim.mfw.usecase;


public interface UseCaseListener<Res extends TaskResult> {

	void onTaskStart(UseCase useCase);
	void onTaskUpdate(Res result);
	void onTaskComplete(Res result);
	void onUndone(Res result);
	void onTaskCancel();
}
