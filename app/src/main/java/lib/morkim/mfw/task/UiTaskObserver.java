package lib.morkim.mfw.task;

import android.os.Handler;
import android.os.Looper;

import java.util.Observable;
import java.util.Observer;

@SuppressWarnings({"WeakerAccess", "UnusedParameters"})
public class UiTaskObserver<E extends ScheduledTask> implements Observer {

	@Override
	public void update(final Observable observable, final Object data) {

		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				//noinspection unchecked
				onTaskUpdate((E) observable, data);
			}
		});
	}

	public void onTaskUpdate(E observable, Object data) {

	}
}
