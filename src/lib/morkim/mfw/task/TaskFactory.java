package lib.morkim.mfw.task;

import lib.morkim.mfw.app.AppContext;

public abstract class TaskFactory {

    private AppContext appContext;

	public TaskFactory(AppContext appContext) {
        this.appContext = appContext;
    }

	public abstract ScheduledTask createTask(String taskName);

	public AppContext getAppContext() {
		return appContext;
	}

	public void setAppContext(AppContext appContext) {
		this.appContext = appContext;
	}

}
