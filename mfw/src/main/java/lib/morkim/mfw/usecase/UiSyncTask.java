package lib.morkim.mfw.usecase;


import android.os.Handler;
import android.os.Looper;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class UiSyncTask<A extends MorkimApp<M, ?>, M extends Model, Req extends TaskRequest, Res extends TaskResult>
		extends UseCase<A, M, Req, Res> {

	public UiSyncTask(A morkimApp, UseCaseListener<Res> listener) {
		super(morkimApp, listener);
	}

	@Override
	protected void updateListener(final Res result) {

		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				UiSyncTask.super.updateListener(result);
			}
		});
	}
}
