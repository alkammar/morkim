package lib.morkim.mfw.usecase;

import lib.morkim.mfw.app.AppContext;

public abstract class SyncUseCase extends BaseUseCase implements UseCaseStateListener {

	public SyncUseCase(AppContext appContext) {
		super(appContext);
	}
	
	@Override
	public void execute(UseCaseRequest request) {
		
		setRequest(request);
		
		onPrepare();
		onExecute();
		onSaveModel();
	}
	
	@Override
	public void execute() {
		execute(null);
	}

	@Override
	public void executeSync() {
		this.execute();
	}
	
	@Override
	public void executeSync(UseCaseRequest request) {
		execute(request);
	}

	@Override
	protected void reportProgress() {
		
	}
	
	@Override
	public void onUseCaseComplete(UseCaseResult response) {
		
	}
	
	@Override
	protected void onSaveModel() {}
}