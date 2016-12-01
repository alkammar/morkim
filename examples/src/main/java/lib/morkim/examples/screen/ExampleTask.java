package lib.morkim.examples.screen;

import android.os.SystemClock;

import lib.morkim.examples.app.ExampleApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.usecase.MorkimAsyncTask;
import lib.morkim.mfw.usecase.MorkimTaskListener;
import lib.morkim.mfw.usecase.TaskRequest;

class ExampleTask extends MorkimAsyncTask<ExampleApp, Model, TaskRequest, ExampleResult> {

	ExampleTask(ExampleApp appContext, MorkimTaskListener<ExampleResult> morkimTaskListener) {
		super(appContext, morkimTaskListener);
	}

	@Override
	protected ExampleResult onExecute(TaskRequest request) {

		ExampleResult result = new ExampleResult();
		result.setCompletionPercent(0);

		for (int i = 0; i <= 101; i++) {
			updateProgress(result);

			SystemClock.sleep(200);

			result.count = i;
		}

		result.setCompletionPercent(100);

		return result;
	}
}
