package lib.morkim.examples.usecase;

import android.os.SystemClock;

import lib.morkim.examples.app.ExampleApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.usecase.AsyncUseCase;
import lib.morkim.mfw.usecase.UseCaseListener;
import lib.morkim.mfw.usecase.UseCaseRequest;

public class ReusableTask extends AsyncUseCase<ExampleApp, Model, UseCaseRequest, ReusableResult> {

	public ReusableTask(ExampleApp appContext, UseCaseListener<ReusableResult> useCaseListener) {
		super(appContext, useCaseListener);
	}

	@Override
	protected ReusableResult onExecute(UseCaseRequest request) {

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

	@Override
	protected ReusableResult onUndo(UseCaseRequest request) {
		return null;
	}
}
