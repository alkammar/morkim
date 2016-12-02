package lib.morkim.mfw.app;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import android.util.Log;

public class Scheduler {

	private ScheduledExecutorService scheduler =
			Executors.newSingleThreadScheduledExecutor();
	
	private Runnable task;

	public Scheduler(int seconds, Runnable task) {
		
		this.task = task;
		
		final ScheduledFuture<?> handle = scheduler.scheduleAtFixedRate(
				task, 
				0, 
				seconds, 
				TimeUnit.SECONDS);

		
		Thread exceptionCatcher = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					handle.get();
					// dead code here?
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (CancellationException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					Log.e("Uncaught exception in scheduled execution", e.getCause().toString());
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					throw new RuntimeException(sw.toString());
				}
			}
		});
		
		exceptionCatcher.start();
	}

	public void unschedule() {
		scheduler.shutdown();
	}

	public Runnable getTask() {
		return task;
	}
}
