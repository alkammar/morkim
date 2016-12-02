package lib.morkim.mfw.usecase;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.repo.Repository;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class MorkimTask<A extends MorkimApp<M, ?>, M extends Model, Req extends TaskRequest, Res extends TaskResult> {

	protected A appContext;
	protected M model;
	protected Repository repo;

	protected MorkimTaskListener<Res> listener;

	private Req request;

	public MorkimTask(A morkimApp, MorkimTaskListener<Res> listener) {

		this.appContext = morkimApp;
		this.model = appContext.getModel();
		this.repo = appContext.getRepo();

		if (listener == null)
			this.listener = new MorkimTaskListener<Res>() {
				@Override
				public void onTaskStart(MorkimTask task) {}

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

	public void execute() {
		executeSync();
	}

	public void execute(Req request) {
		executeSync(request);
	}

	public void executeSync() {
		onExecute(null);
	}

	public void executeSync(Req request) {
		setRequest(request);
		onExecute(request);
	}

	public void cancel() {

	}

	protected void updateProgress(Res result) {
		updateListener(result);
	}

	protected abstract Res onExecute(Req request);

	protected void updateListener(Res result) {

		if (result != null) {
			if (result.completionPercent != 100)
				listener.onTaskUpdate(result);
			else
				listener.onTaskComplete(result);
		} else
			listener.onTaskComplete(null);
	}

	protected void onSaveModel() {}

	public void setAppContext(A appContext) {
		this.appContext = appContext;
	}

	protected Req getRequest() {
		return request;
	}

	public void setRequest(Req request) {
		this.request = request;
	}

	public MorkimTaskListener<Res> getListener() {
		return listener;
	}

	public void setListener(MorkimTaskListener<Res> listener) {
		this.listener = listener;
	}
}
