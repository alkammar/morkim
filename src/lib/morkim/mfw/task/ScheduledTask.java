package lib.morkim.mfw.task;

import java.util.Observable;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.repo.Repository;
import android.os.Handler;
import android.os.Looper;

public abstract class ScheduledTask extends Observable implements Runnable {

	private AppContext appContext;
	
	private Model model;
	private Repository repos;

	public ScheduledTask(AppContext appContext) {
		
		this.appContext = appContext;
		
		this.model = ((MorkimApp) appContext).getModel();
		this.repos = ((MorkimApp) appContext).getRepos();
	}

	@Override
	public void run() {
		onExecute();
//		onReportProgress();
		onSaveModel();
	}

	protected void onExecute() {}
	protected void onSaveModel() {}

	public abstract int getPeriod();

	@Override
	public void notifyObservers(final Object data) {
		new Handler(Looper.getMainLooper()).post(new Runnable() {

			@Override
			public void run() {
				notifyObserversOnUiThread(data);
			}
		});
	}
	
	private void notifyObserversOnUiThread(Object data) {
		super.notifyObservers(data);
	}

	public AppContext getAppContext() {
		return appContext;
	}

	public void setAppContext(AppContext appContext) {
		this.appContext = appContext;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Repository getRepos() {
		return repos;
	}

	public void setRepos(Repository repos) {
		this.repos = repos;
	}

}
