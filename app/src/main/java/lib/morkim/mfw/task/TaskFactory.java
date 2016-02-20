package lib.morkim.mfw.task;

import lib.morkim.mfw.app.MorkimApp;

public abstract class TaskFactory {

    private MorkimApp appContext;

	public TaskFactory(MorkimApp appContext) {
        this.appContext = appContext;
    }

	public abstract ScheduledTask createTask(String taskName);

	public MorkimApp getAppContext() {
		return appContext;
	}

	public void setAppContext(MorkimApp appContext) {
		this.appContext = appContext;
	}

}
