package lib.morkim.mfw.usecase;

import android.os.AsyncTask;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.repo.gateway.GatewayPersistException;

@SuppressWarnings({"WeakerAccess", "unused", "unchecked"})
public abstract class MorkimAsyncTask<A extends MorkimApp<M, ?>, M extends Model, Req extends TaskRequest, Res extends TaskResult>
		extends MorkimTask<A, M, Req, Res> {

	private Task asyncTask;

	public MorkimAsyncTask(A appContext) {
		this(appContext, null);
	}

	public MorkimAsyncTask(A morkimApp, MorkimTaskListener<Res> listener) {
		super(morkimApp, listener);

		this.asyncTask = new Task();

	}

	public MorkimAsyncTask() {
		super();

		this.asyncTask = new Task();
	}

	@SuppressWarnings("unchecked")
	private class Task extends AsyncTask<Req, Res, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			listener.onTaskStart(MorkimAsyncTask.this);
		}

		@Override
		protected Void doInBackground(Req... params) {

			if (params.length > 0)
				setRequest(params[0]);

			publishProgress(onExecute(getRequest()));

			try {
				MorkimAsyncTask.this.onPostExecute();
			} catch (GatewayPersistException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Res... values) {

			updateListener(values[0]);
		}

		void updateProgress(Res result) {
			publishProgress(result);
		}
	}

	@Override
	public void execute(Req request) {
		subscribedListeners = appContext.getUseCaseSubscriptions(this.getClass());
		asyncTask.execute(request);
	}

	@Override
	public void execute() {
		subscribedListeners = appContext.getUseCaseSubscriptions(this.getClass());
		asyncTask.execute();
	}

	@Override
	public void cancel() {
		asyncTask.cancel(true);
	}

	@Override
	protected void updateProgress(Res result) {
		asyncTask.updateProgress(result);
	}

}