package lib.morkim.mfw.usecase;

import android.os.AsyncTask;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.repo.Repository;

public abstract class UseCase<Req extends UseCaseRequest, Res extends UseCaseResponse>
		extends AsyncTask<Req, Res, Void> {

	private AppContext appContext;
	private Req request;
	private UseCaseStateListener listener;

	public UseCase(AppContext appContext, UseCaseStateListener listener) {
		this.appContext = appContext;
		
		if (listener == null)
		this.listener = new UseCaseStateListener() {
			@Override
			public void onUseCaseStart(UseCase useCase) {

			}

			@Override
			public void onUseCaseUpdate(UseCaseProgress response) {

			}

			@Override
			public void onUseCaseComplete(UseCaseResponse response) {

			}

			@Override
			public void onUseCaseCancel() {

			}
		};
		else
			this.listener = listener;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		listener.onUseCaseStart(this);
	}

	@Override
	protected Void doInBackground(Req... params) {

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

	public void executeSync(Req request) {

		setRequest(request);

		executeSync();
	}

	protected void reportProgress(Res result) {
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

	protected abstract Res onExecute();
	protected void onSaveModel() {}

	public AppContext getAppContext() {
		return appContext;
	}

	public void setAppContext(AppContext appContext) {
		this.appContext = appContext;
	}

	public Req getRequest() {
		return request;
	}

	public void setRequest(Req request) {
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