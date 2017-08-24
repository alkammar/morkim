package lib.morkim.mfw.usecase;


public class UndoRecord {

	private Class<? extends UseCase> useCaseClass;
	private UseCaseRequest request;

	public UndoRecord(Class<? extends UseCase> useCaseClass, UseCaseRequest request) {
		this.useCaseClass = useCaseClass;
		this.request = request;
	}

	public <u extends UseCase> Class<u> getUseCaseClass() {
		return (Class<u>) useCaseClass;
	}

	public UseCaseRequest getRequest() {
		return request;
	}
}
