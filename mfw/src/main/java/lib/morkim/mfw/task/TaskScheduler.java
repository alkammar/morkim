package lib.morkim.mfw.task;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import lib.morkim.mfw.app.Scheduler;

public class TaskScheduler {

	private TaskFactory factory;

	private Map<Class<? extends ScheduledTask>, Scheduler> schedulers;

	public TaskScheduler(TaskFactory taskFactory) {
		this.factory = taskFactory;

		schedulers = new HashMap<>();
	}

	public <T extends ScheduledTask> ScheduledTask schedule(Class<T> taskClass) {

		ScheduledTask task = factory.createTask(taskClass);

		int period = task.getPeriod();
		Scheduler scheduler = new Scheduler(period, task);

		schedulers.put(taskClass, scheduler);

		return task;
	}

	public <T extends ScheduledTask> void unschedule(Class<T> taskClass) {
		
		Scheduler scheduler = schedulers.get(taskClass);
		
		if (scheduler != null) {
			
			ScheduledTask useCase = (ScheduledTask) scheduler.getTask();
			useCase.deleteObservers();
			
			scheduler.unschedule();
			
			schedulers.remove(taskClass);
		}
	}

	public <T extends ScheduledTask> void register(Class<T> taskClass, UiTaskObserver observer) {

		Scheduler scheduler = schedulers.get(taskClass);

		ScheduledTask task;

		task = scheduler == null ? schedule(taskClass) : (ScheduledTask) scheduler.getTask();

		task.addObserver(observer);
	}

	public <T extends ScheduledTask> void unregister(Class<T> taskClass, UiTaskObserver observer) {

		Scheduler scheduler = schedulers.get(taskClass);

		if (scheduler != null)
			((Observable) scheduler.getTask()).deleteObserver(observer);

	}
}
