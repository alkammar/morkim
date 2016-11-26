package lib.morkim.mfw.ui;


public abstract class AbstractUpdateListenerPending {

	protected Controller.PendingEventsExecutor pendingEventsExecutor;

	void setPendingEventsExecutor(Controller.PendingEventsExecutor pendingEventsExecutor) {
		this.pendingEventsExecutor = pendingEventsExecutor;
	}
}
