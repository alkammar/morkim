package lib.morkim.mfw.ui;

import android.app.Activity;

import java.util.Observable;
import java.util.Observer;

import lib.morkim.mfw.domain.Entity;

public class UiEntityObserver<E extends Entity> implements Observer {

	private Controller controller;

	public UiEntityObserver(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void update(final Observable observable, final Object data) {

		Activity activity = controller.getActivity();
		if (activity != null)
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					update(observable, data);
				}
			});
		else
			update(observable, data);
	}

	private void update(E observable, Object data) {
		onEntityUpdated(observable, data);
	}

	public void onEntityUpdated(E observable, Object data) {

	}
}
