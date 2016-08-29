package lib.morkim.mfw.ui;

import android.app.Activity;

import java.util.Observable;
import java.util.Observer;

import lib.morkim.mfw.domain.Entity;

public class UiEntityObserver<E extends Entity> implements Observer {

	private Activity activity;

	public UiEntityObserver(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void update(final Observable observable, final Object data) {
		activity.runOnUiThread(new Runnable() {
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
