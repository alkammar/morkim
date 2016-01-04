package lib.morkim.mfw.usecase;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.repo.Repository;
import android.os.AsyncTask;

public abstract class UseCase extends AsyncTask<UseCaseRequest, UseCaseResponse, Void> {

	private AppContext appContext;
	private UseCaseRequest request;
	private UseCaseStateListener listener;

	public UseCase(AppContext appContext) {
		this.appContext = appContext;
		this.listener = new UseCaseStateListener() {

			@Override
			public void onUseCaseStart(UseCase useCase) {}

			@Override
			public void onUseCaseUpdate(UseCaseProgress response) {}

			@Override
			public void onUseCaseComplete(UseCaseResult response) {}

			@Override
			public void onUseCaseCancel() {}
		};
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		listener.onUseCaseStart(this);
	}

	@Override
	protected Void doInBackground(UseCaseRequest... params) {

		if (params.length > 0)
			setRequest(params[0]);

		executeSync();

		return (Void) null;
	}

	public void executeSync() {

		onPrepare();
		publishProgress(onExecute());
		onSaveModel();
	}

	public void executeSync(UseCaseRequest request) {

		setRequest(request);

		executeSync();
	}

	protected void reportProgress(UseCaseProgress result) {
		publishProgress(result);
	}

	@Override
	protected void onProgressUpdate(UseCaseResponse... values) {

		UseCaseResponse response = values[0];
		if (response != null) {
			if (response instanceof UseCaseProgress)
				listener.onUseCaseUpdate((UseCaseProgress) response);
			else if (response instanceof UseCaseResult)
				listener.onUseCaseComplete((UseCaseResult) response);
		} else
			listener.onUseCaseComplete((UseCaseResult) response);
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}

	protected void onPrepare() {}

	protected abstract UseCaseResult onExecute();
	protected void onSaveModel() {}

	public AppContext getAppContext() {
		return appContext;
	}

	public void setAppContext(AppContext appContext) {
		this.appContext = appContext;
	}

	public UseCaseRequest getRequest() {
		return request;
	}

	public void setRequest(UseCaseRequest request) {
		this.request = request;
	}

	public UseCaseStateListener getListener() {
		return listener;
	}

	public void setListener(UseCaseStateListener listener) {
		this.listener = listener;
	}

	public Model getModel() {
		return appContext.getModel();
	}

	public Repository getRepos() {
		return ((MorkimApp) appContext).getRepos();	
	}
}