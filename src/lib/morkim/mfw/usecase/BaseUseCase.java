package lib.morkim.mfw.usecase;

import java.util.Observable;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.repo.Repository;

public abstract class BaseUseCase extends Observable implements UseCase {

	private AppContext appContext;
	private Model model;
	private Repository repos;

	private UseCaseRequest request;
	private UseCaseStateListener listener;

	public BaseUseCase(AppContext appContext) {

		this.appContext = appContext;
		this.model = ((MorkimApp) appContext).getModel();
		this.repos = ((MorkimApp) appContext).getRepos();
	}

	protected void onPrepare() {
		if (request != null)
			listener = request.listener;
	}

	protected abstract void reportProgress();

	protected abstract void onExecute();
	protected abstract void onReportProgress();
	protected abstract void onSaveModel();

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
	public UseCaseRequest getRequest() {
		return request;
	}
	public void setRequest(UseCaseRequest request) {
		this.request = request;
	}
	public Repository getRepos() {
		return repos;
	}
	public void setRepos(Repository repos) {
		this.repos = repos;
	}
	public UseCaseStateListener getListener() {
		return listener;
	}
}
