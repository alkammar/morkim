package lib.morkim.mfw.usecase;

import java.util.List;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.repo.Repository;
import lib.morkim.mfw.repo.gateway.GatewayPersistException;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class MorkimTask<A extends MorkimApp<M, ?>, M extends Model, Req extends TaskRequest, Res extends TaskResult> {

	@TaskDependency
	protected A appContext;
	@TaskDependency
	protected M model;
	@TaskDependency
	protected Repository repo;

	protected MorkimTaskListener<Res> listener = new MorkimTaskListener<Res>() {
		@Override
		public void onTaskStart(MorkimTask task) {}

		@Override
		public void onTaskUpdate(Res result) {}

		@Override
		public void onTaskComplete(Res result) {}

		@Override
		public void onTaskCancel() {}
	};

	protected List<MorkimTaskListener<? extends TaskResult>> subscribedListeners;

	private Req request;

	public MorkimTask(A morkimApp, MorkimTaskListener<Res> listener) {

		this.appContext = morkimApp;
		this.model = appContext.getModel();
		this.repo = appContext.getRepo();

		if (listener != null)
			this.listener = listener;
	}

	public MorkimTask() {

	}

	public void execute() {
		executeSync();
	}

	public void execute(Req request) {
		executeSync(request);
	}

	public void executeSync() {
		executeSync(null);
	}

	public void executeSync(Req request) {

		subscribedListeners = appContext.getUseCaseSubscriptions(this.getClass());

		setRequest(request);
		onExecute(request);
		try {
			onPostExecute();
		} catch (GatewayPersistException e) {
			e.printStackTrace();
		}
	}

	public void cancel() {

	}

	protected void updateProgress(Res result) {
		updateListener(result);
	}

	protected abstract Res onExecute(Req request);

	protected void updateListener(Res result) {

		if (result != null) {
			if (result.completionPercent != 100) {
				listener.onTaskUpdate(result);

				if (subscribedListeners != null)
					for (MorkimTaskListener subscribedListener : subscribedListeners)
						if (!subscribedListener.equals(MorkimTask.this.listener))
							subscribedListener.onTaskUpdate(result);
			} else {
				listener.onTaskComplete(result);

				if (subscribedListeners != null)
					for (MorkimTaskListener subsribedListener : subscribedListeners)
						if (!subsribedListener.equals(MorkimTask.this.listener))
							subsribedListener.onTaskComplete(result);
			}
		} else
			listener.onTaskComplete(null);
	}

	protected void onPostExecute() throws GatewayPersistException {}

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

	public MorkimTask setListener(MorkimTaskListener<Res> listener) {
		this.listener = listener;

		return this;
	}
}
