package lib.morkim.mfw.app;

import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.task.TaskScheduler;
import android.content.Context;

public interface AppContext {

	public Context getContext();
	public String getString(int id);
	
	public Model getModel();
	
	public TaskScheduler getTaskScheduler();
	public abstract Analytics getAnalytics();
}