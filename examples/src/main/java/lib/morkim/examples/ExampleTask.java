package lib.morkim.examples;

import android.os.SystemClock;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.usecase.MorkimTask;
import lib.morkim.mfw.usecase.MorkimTaskListener;
import lib.morkim.mfw.usecase.TaskRequest;

public class ExampleTask extends MorkimTask<TaskRequest, ExampleResult> {

	public ExampleTask(MorkimApp appContext, MorkimTaskListener<ExampleResult> morkimTaskListener) {
		super(appContext, morkimTaskListener);
	}

	@Override
	protected ExampleResult onExecute() {

		ExampleResult exampleResult = new ExampleResult();
		exampleResult.setCompletionPercent(0);

		for (int i = 0; i <= 101; i++) {
			publishProgress(exampleResult);

			SystemClock.sleep(200);

			exampleResult.count = i;
		}

		return null;
	}
}
