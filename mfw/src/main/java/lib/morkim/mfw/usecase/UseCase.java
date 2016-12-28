package lib.morkim.mfw.usecase;

import java.util.List;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.repo.Repository;
import lib.morkim.mfw.repo.gateway.GatewayPersistException;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class UseCase<A extends MorkimApp<M, ?>, M extends Model, Req extends TaskRequest, Res extends TaskResult> {

	@TaskDependency
	protected A appContext;
	@TaskDependency
	protected M model;
	@TaskDependency
	protected Repository repo;

	protected boolean isUndoing;

	protected UseCaseListener<Res> listener = new UseCaseListener<Res>() {
		@Override
		public void onTaskStart(UseCase task) {}

		@Override
		public void onTaskUpdate(Res result) {}

		@Override
		public void onTaskComplete(Res result) {}

		@Override
		public void onUndone(Res result) {

		}

		@Override
		public void onTaskCancel() {}
	};

	protected List<UseCaseListener<? extends TaskResult>> subscribedListeners;

	private Req request;

	public UseCase(A morkimApp, UseCaseListener<Res> listener) {

		this.appContext = morkimApp;
		this.model = appContext.getModel();
		this.repo = appContext.getRepo();

		if (listener != null)
			this.listener = listener;
	}

	public UseCase() {

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

		// TODO getUseCaseSubscriptions should be from implemented interface or dependency
		subscribedListeners = appContext.getUseCaseSubscriptions(this.getClass());

		setRequest(request);
		onExecute(request);
		try {
			onPostExecute();
		} catch (GatewayPersistException e) {
			e.printStackTrace();
		}
	}

	public void undo() {
		// TODO getUseCaseSubscriptions should be from implemented interface or dependency
		subscribedListeners = appContext.getUseCaseSubscriptions(this.getClass());
		onUndo(getRequest());
	}

	public void undoSync() {
		undo();
	}

	public void cancel() {

	}

	protected void updateProgress(Res result) {
		updateListener(result);
	}

	protected abstract Res onExecute(Req request);
	protected abstract Res onUndo(Req request);

	protected void updateListener(Res result) {

		if (result != null) {
			if (result.completionPercent != 100) {
				updateListenerUpdate(result);
			} else if (!isUndoing) {
				updateListenerComplete(result);

				if (canUndo())
					// TODO addToUndoStack should be from implemented interface or dependency
					appContext.addToUndoStack(this, getRequest());
			} else {
				updateListenerUndo(result);

				isUndoing = false;
			}
		} else {
			if (!isUndoing)
				updateListenerComplete(null);
			else {
				updateListenerUndo(null);
				isUndoing = false;
			}
		}
	}

	private void updateListenerUpdate(Res result) {
		listener.onTaskUpdate(result);

		if (subscribedListeners != null)
			for (UseCaseListener subscribedListener : subscribedListeners)
				if (!subscribedListener.equals(UseCase.this.listener))
					subscribedListener.onTaskUpdate(result);
	}

	private void updateListenerComplete(Res result) {
		listener.onTaskComplete(result);

		if (subscribedListeners != null)
			for (UseCaseListener subscribedListener : subscribedListeners)
				if (!subscribedListener.equals(UseCase.this.listener))
					subscribedListener.onTaskComplete(result);
	}

	private void updateListenerUndo(Res result) {
		listener.onUndone(result);

		if (subscribedListeners != null)
			for (UseCaseListener subscribedListener : subscribedListeners)
				if (!subscribedListener.equals(UseCase.this.listener))
					subscribedListener.onUndone(result);
	}

	protected void onPostExecute() throws GatewayPersistException {}

	public void setAppContext(A appContext) {
		this.appContext = appContext;
	}

	protected Req getRequest() {
		return request;
	}

	public UseCase<A, M, Req, Res> setRequest(Req request) {
		this.request = request;

		return this;
	}

	public UseCaseListener<Res> getListener() {
		return listener;
	}

	public UseCase setListener(UseCaseListener<Res> listener) {
		this.listener = listener;

		return this;
	}

	protected boolean canUndo() {
		return false;
	}
}