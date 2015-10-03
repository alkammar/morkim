package lib.morkim.mfw.task;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import lib.morkim.mfw.app.Scheduler;
import lib.morkim.mfw.usecase.UseCaseFactory;

public class TaskScheduler {

	private TaskFactory factory;

	private Map<String, Scheduler> schedulers;

	public TaskScheduler(TaskFactory taskFactory) {
		this.factory = taskFactory;

		schedulers = new HashMap<String, Scheduler>();
	}

	public ScheduledTask schedule(String taskName) {

		ScheduledTask task = (ScheduledTask) factory.createTask(taskName);

		int period = ((ScheduledTask) task).getPeriod();
		Scheduler scheduler = new Scheduler(period, (Runnable) task);

		schedulers.put(taskName, scheduler);

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

	public void unregister(String taskName, Observer observer) {

		Scheduler scheduler = schedulers.get(taskName);

		if (scheduler != null)
			((Observable) scheduler.getTask()).deleteObserver(observer);

	}
}
