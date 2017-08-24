package lib.morkim.mfw.usecase;


public interface UseCaseListener<Res extends UseCaseResult> {

	void onTaskStart(UseCase useCase);
	void onTaskUpdate(Res result);
	void onTaskComplete(Res result);
	void onUndone(Res result);
	void onTaskCancel();

	boolean onTaskError(Res errorResult);

	void onTaskAborted();
}
