package lib.morkim.examples;

import android.os.SystemClock;

import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.usecase.MorkimTask;
import lib.morkim.mfw.usecase.MorkimTaskListener;
import lib.morkim.mfw.usecase.TaskRequest;

public class ExampleTask extends MorkimTask<ExampleApp, Model, TaskRequest, ExampleResult> {

	public ExampleTask(ExampleApp appContext, MorkimTaskListener<ExampleResult> morkimTaskListener) {
		super(appContext, morkimTaskListener);
	}

	@Override
	protected ExampleResult onExecute() {

		ExampleResult result = new ExampleResult();
		result.setCompletionPercent(0);

		for (int i = 0; i <= 101; i++) {
			publishProgress(result);

			SystemClock.sleep(200);

			result.count = i;
		}

		result.setCompletionPercent(100);

		return result;
	}
}
