package lib.morkim.examples.screen.fragment;

import android.view.View;

import lib.morkim.examples.app.ExampleApp;
import lib.morkim.examples.usecase.ExampleResult;
import lib.morkim.examples.usecase.ExampleTask;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.ui.Controller;
import lib.morkim.mfw.ui.Viewable;
import lib.morkim.mfw.usecase.UseCaseListener;
import lib.morkim.mfw.usecase.OnTaskUpdateListener;
import lib.morkim.mfw.usecase.UseCaseSubscription;

class ExampleFragmentController extends Controller<ExampleApp, Model, ExampleFragmentUpdateListener> {

	private ExampleParentListener parentListener;

	@Override
	public void onAttachViewable(Viewable<?, ?> viewable) {
		super.onAttachViewable(viewable);

		parentListener = viewable.getParentListener();
	}

	View.OnClickListener buttonClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			parentListener.onDoSomethingWhenButtonClicked();
		}
	};

	@UseCaseSubscription({ExampleTask.class, ExampleTask.class})
	private UseCaseListener<ExampleResult> exampleTaskListener = new OnTaskUpdateListener<ExampleResult>() {

		@Override
		public void onTaskUpdate(ExampleResult result) {
			getUpdateListener().getClass();
		}

		@Override
		public void onTaskComplete(ExampleResult result) {
			getUpdateListener().doFragmentAction();
		}

		@Override
		public void onUndone(ExampleResult result) {

		}

		@Override
		public boolean onTaskError(ExampleResult errorResult) {
			return false;
		}

		@Override
		public void onTaskAborted() {

		}
	};
}
