package lib.morkim.mfw.usecase;


public interface UseCaseStateListener<Res extends UseCaseResponse> {

	public void onUseCaseStart(UseCase useCase);
	public void onUseCaseUpdate(UseCaseProgress response);
	public void onUseCaseComplete(Res response);
	public void onUseCaseCancel();
}
