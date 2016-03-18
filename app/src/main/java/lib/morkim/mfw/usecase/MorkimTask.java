package lib.morkim.mfw.usecase;

import android.os.AsyncTask;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.repo.Repository;

public abstract class MorkimTask<Req extends TaskRequest, Res extends TaskResult>
		extends AsyncTask<Req, Res, Void> {

	private MorkimApp appContext;
	private Req request;
	private MorkimTaskListener<Res> listener;

	public MorkimTask(MorkimApp appContext) {
		this(appContext, null);
	}

	public MorkimTask(MorkimApp morkimApp, MorkimTaskListener<Res> listener) {
		this.appContext = morkimApp;
		
		if (listener == null)
			this.listener = new MorkimTaskListener<Res>() {
				@Override
				public void onTaskStart(MorkimTask useCase) {}

				@Override
				public void onTaskUpdate(Res result) {}

				@Override
				public void onTaskComplete(Res result) {}

				@Override
				public void onTaskCancel() {}
			};
		else
			this.listener = listener;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		listener.onTaskStart(this);
	}

	@Override
	protected Void doInBackground(Req... params) {

		if (params.length > 0)
			setRequest(params[0]);

		executeSync();

		return null;
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
	protected void onProgressUpdate(Res... values) {

		Res result = values[0];
		if (result != null) {
			if (result.completionPercent != 100)
				listener.onTaskUpdate(result);
			else
				listener.onTaskComplete(result);
		} else
			listener.onTaskComplete(null);
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}

	protected void onPrepare() {}

	protected abstract Res onExecute();
	protected void onSaveModel() {}

	public void setAppContext(MorkimApp appContext) {
		this.appContext = appContext;
	}
	protected MorkimApp getAppContext() {
		return appContext;
	}

	protected Req getRequest() {
		return request;
	}

	public void setRequest(Req request) {
		this.request = request;
	}

	public MorkimTaskListener getListener() {
		return listener;
	}

	public void setListener(MorkimTaskListener<Res> listener) {
		this.listener = listener;
	}

	public Model getModel() {
		return appContext.getModel();
	}

	protected Repository getRepos() {
		return appContext.getRepos();
	}
}