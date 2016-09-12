package lib.morkim.examples;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.repo.MorkimRepository;
import lib.morkim.mfw.task.ScheduledTask;

public class ExampleScheduledTask extends ScheduledTask<Model> {

	public ExampleScheduledTask(MorkimApp<Model, MorkimRepository> appContext) {
		super(appContext);
	}

	@Override
	public int getPeriod() {
		return 0;
	}
}
