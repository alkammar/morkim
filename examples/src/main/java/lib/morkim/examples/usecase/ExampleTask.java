package lib.morkim.examples.usecase;

import android.os.SystemClock;

import lib.morkim.examples.app.ExampleApp;
import lib.morkim.examples.backendinterfaces.BackEndCall;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.usecase.MorkimAsyncTask;
import lib.morkim.mfw.usecase.TaskDependency;
import lib.morkim.mfw.usecase.TaskRequest;

public class ExampleTask extends MorkimAsyncTask<ExampleApp, Model, TaskRequest, ExampleResult> {

	@TaskDependency
	private BackEndCall backEndCall;

//	public ExampleTask() {
//		super();
//	}
//
//	public ExampleTask(ExampleApp appContext, MorkimTaskListener<ExampleResult> morkimTaskListener) {
//		super(appContext, morkimTaskListener);
//	}

	@Override
	protected ExampleResult onExecute(TaskRequest request) {

		backEndCall.send(new BackEndCall.Request());

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
