package lib.morkim.examples.usecase;

import android.os.SystemClock;

import lib.morkim.examples.app.ExampleApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.usecase.MorkimAsyncTask;
import lib.morkim.mfw.usecase.MorkimTaskListener;
import lib.morkim.mfw.usecase.TaskRequest;

public class ReusableTask extends MorkimAsyncTask<ExampleApp, Model, TaskRequest, ReusableResult> {

	public ReusableTask(ExampleApp appContext, MorkimTaskListener<ReusableResult> morkimTaskListener) {
		super(appContext, morkimTaskListener);
	}

	@Override
	protected ReusableResult onExecute(TaskRequest request) {

		ReusableResult result = new ReusableResult();
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
