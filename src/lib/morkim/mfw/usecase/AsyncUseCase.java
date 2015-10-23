package lib.morkim.mfw.usecase;

import lib.morkim.mfw.app.AppContext;

public abstract class AsyncUseCase extends BaseUseCase implements UseCase {

	private UseCaseTask asycnTask;

	public AsyncUseCase(AppContext appContext) {
		super(appContext);
		
		this.asycnTask = new UseCaseTask(this);
	}

	@Override
	public void execute() {
		execute(null);
	}

	@Override
	public void execute(UseCaseRequest request) {
		
		setRequest(request);
		
		asycnTask.execute();
	}
	
	@Override
	public void executeSync() {

		this.onPrepare();
		this.onExecute();
		this.onReportProgress();
		this.onSaveModel();
	}
	
	@Override
	public void executeSync(UseCaseRequest request) {
		setRequest(request);
		
		executeSync();
	}
	
	@Override
	protected void reportProgress() {
		asycnTask.reportProgress();
	}

	public void setListener(UseCaseStateListener listener) {
	}
}