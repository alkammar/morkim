package lib.morkim.mfw.usecase;


public interface UseCaseStateListener {

	public void onUseCaseStart(UseCase useCase);
	public void onUseCaseUpdate(UseCaseProgress response);
	public void onUseCaseComplete(UseCaseResult response);
	public void onUseCaseCancel();
}
