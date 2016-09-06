package lib.morkim.mfw.ui;

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

		controller.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				//noinspection unchecked
				onEntityUpdated((E) observable, data);
			}
		});
	}

	public void onEntityUpdated(E observable, Object data) {

	}
}
