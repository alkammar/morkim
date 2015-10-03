package lib.morkim.mfw.usecase;



public interface UseCase {
	
	public void execute(UseCaseRequest request);
	public void execute();
	
	public void executeSync(UseCaseRequest request);
	public void executeSync();
}
