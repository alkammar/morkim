package lib.morkim.mfw.usecase;


public interface UseCaseStateListener<Prog extends UseCaseProgress, Res extends UseCaseResponse> {

	public void onUseCaseStart(UseCase useCase);
	public void onUseCaseUpdate(Prog response);
	public void onUseCaseComplete(Res response);
	public void onUseCaseCancel();
}
