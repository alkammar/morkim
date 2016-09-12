package lib.morkim.mfw.task;

public abstract class TaskFactory<A> {

    private A appContext;

	public TaskFactory(A appContext) {
        this.appContext = appContext;
    }

	public abstract ScheduledTask createTask(String taskName);

	public abstract <T extends ScheduledTask> ScheduledTask createTask(Class<T> taskClass);

	public A getAppContext() {
		return appContext;
	}

	public void setAppContext(A appContext) {
		this.appContext = appContext;
	}
}
