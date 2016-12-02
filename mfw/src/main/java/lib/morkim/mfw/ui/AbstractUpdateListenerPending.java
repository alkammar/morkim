package lib.morkim.mfw.ui;


public abstract class AbstractUpdateListenerPending<U> {

	protected Controller.PendingEventsExecutor pendingEventsExecutor;
	protected U updateListener;

	void setPendingEventsExecutor(Controller.PendingEventsExecutor pendingEventsExecutor) {
		this.pendingEventsExecutor = pendingEventsExecutor;
	}

	void setUpdateListener(U updateListener) {
		this.updateListener = updateListener;
	}
}
