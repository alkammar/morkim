package lib.morkim.mfw.app;

import android.content.Context;

import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.task.TaskScheduler;

public interface AppContext<M extends Model> {

	public Context getContext();
	public String getString(int id);
	
	public M getModel();
	
	public TaskScheduler getTaskScheduler();
	public abstract Analytics getAnalytics();
}
