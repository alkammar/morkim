package lib.morkim.mfw.task;

import java.util.Observable;
import java.util.Observer;

import lib.morkim.mfw.ui.Controller;

public class UiTaskObserver<E extends ScheduledTask> implements Observer {

	private Controller controller;

	public UiTaskObserver(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void update(final Observable observable, final Object data) {

		controller.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				//noinspection unchecked
				onTaskUpdate((E) observable);
			}
		});
	}

	public void onTaskUpdate(E observable) {

	}
}
