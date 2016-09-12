package lib.morkim.mfw.task;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import lib.morkim.mfw.app.Scheduler;

public class TaskScheduler {

	private TaskFactory factory;

	private Map<String, Scheduler> schedulers;
	private Map<Class<? extends ScheduledTask>, Scheduler> schedulers2;

	public TaskScheduler(TaskFactory taskFactory) {
		this.factory = taskFactory;

		schedulers = new HashMap<>();
		schedulers2 = new HashMap<>();
	}

	public ScheduledTask schedule(String taskName) {

		ScheduledTask task = factory.createTask(taskName);

		int period = task.getPeriod();
		Scheduler scheduler = new Scheduler(period, task);

		schedulers.put(taskName, scheduler);

		return task;
	}

	public <T extends ScheduledTask> ScheduledTask schedule(Class<T> taskClass) {

		ScheduledTask task = factory.createTask(taskClass);

		int period = task.getPeriod();
		Scheduler scheduler = new Scheduler(period, task);

		schedulers2.put(taskClass, scheduler);

		return task;
	}

	public void unschedule(String taskName) {
		
		Scheduler scheduler = schedulers.get(taskName);
		
		if (scheduler != null) {
			
			ScheduledTask useCase = (ScheduledTask) scheduler.getTask();
			useCase.deleteObservers();
			
			scheduler.unschedule();
			
			schedulers.remove(taskName);
		}
	}

	public void register(String taskName, Observer observer) {

		Scheduler scheduler = schedulers.get(taskName);

		ScheduledTask useCase;

		if (scheduler == null)
			useCase = schedule(taskName);
		else
			useCase = (ScheduledTask) scheduler.getTask();

		useCase.addObserver(observer);
	}


	public <T extends ScheduledTask> void register(Class<T> taskClass, Observer observer) {

		Scheduler scheduler = schedulers2.get(taskClass);

		ScheduledTask task;

		if (scheduler == null)
			task = schedule(taskClass);
		else
			task = (ScheduledTask) scheduler.getTask();

		task.addObserver(observer);
	}

	public void unregister(String taskName, Observer observer) {

		Scheduler scheduler = schedulers.get(taskName);

		if (scheduler != null)
			((Observable) scheduler.getTask()).deleteObserver(observer);

	}
}
