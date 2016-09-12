package lib.morkim.examples;

import lib.morkim.mfw.task.ScheduledTask;
import lib.morkim.mfw.task.TaskFactory;

public class ExampleTaskFactory extends TaskFactory<ExampleApp> {

	public ExampleTaskFactory(ExampleApp appContext) {
		super(appContext);
	}

	@Override
	public ScheduledTask createTask(String taskName) {
		return null;
	}

	@Override
	public <T extends ScheduledTask> ScheduledTask createTask(Class<T> taskClass) {

		if (ExampleScheduledTask.class == taskClass)
			return new ExampleScheduledTask(getAppContext());

		return null;
	}
}
