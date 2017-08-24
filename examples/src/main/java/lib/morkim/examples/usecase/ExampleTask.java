package lib.morkim.examples.usecase;

import android.os.SystemClock;

import lib.morkim.examples.app.ExampleApp;
import lib.morkim.examples.backendinterfaces.BackEndCall;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.usecase.AsyncUseCase;
import lib.morkim.mfw.usecase.TaskDependency;
import lib.morkim.mfw.usecase.UseCaseRequest;

public class ExampleTask extends AsyncUseCase<ExampleApp, Model, UseCaseRequest, ExampleResult> {

	@TaskDependency
	private BackEndCall backEndCall;

	@Override
	protected ExampleResult onExecute(UseCaseRequest request) {

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

	@Override
	protected ExampleResult onUndo(UseCaseRequest request) {
		return null;
	}

	@Override
	protected boolean canUndo() {
		return true;
	}
}
