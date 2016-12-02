package lib.morkim.mfw.ui;

import android.os.Handler;
import android.os.Looper;

import java.util.Observable;
import java.util.Observer;

import lib.morkim.mfw.domain.Entity;

@SuppressWarnings({"WeakerAccess", "UnusedParameters"})
public class UiEntityObserver<E extends Entity> {

	public void onEntityUpdated(E observable, Object data) {

	}

	private Observer observer = new Observer() {
		@Override
		public void update(final Observable observable, final Object data) {

			new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				updateObserver(observable, data);
			}
		});
		}
	};

	private void updateObserver(Observable observable, Object data) {
		//noinspection unchecked
		onEntityUpdated((E) observable, data);
	}

	Observer getObserver() {
		return observer;
	}
}
