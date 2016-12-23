package lib.morkim.mfw.usecase;


public class UndoRecord {

	private Class<? extends UseCase> useCaseClass;
	private TaskRequest request;

	public UndoRecord(Class<? extends UseCase> useCaseClass, TaskRequest request) {
		this.useCaseClass = useCaseClass;
		this.request = request;
	}

	public <u extends UseCase> Class<u> getUseCaseClass() {
		return (Class<u>) useCaseClass;
	}

	public TaskRequest getRequest() {
		return request;
	}
}
