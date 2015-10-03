package lib.morkim.mfw.domain;

import java.util.Observable;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.repo.Repository;
import lib.morkim.mfw.repo.gateway.GatewayPersistException;
import lib.morkim.mfw.repo.gateway.GatewayRetrieveException;

public abstract class Model extends Observable {

	private AppContext appContext;
	private Repository repository;

	public Model(AppContext appContext) {
		
		this.appContext = appContext;
		setRepository(((MorkimApp) appContext).getRepos());
	}
	
	public abstract void load() throws GatewayRetrieveException ;
	public abstract void save() throws GatewayPersistException;

	public AppContext getAppContext() {
		return appContext;
	}

	public void setAppContext(AppContext appContext) {
		this.appContext = appContext;
	}

	public Repository getRepo() {
		return repository;
	}

	public void setRepository(Repository repo) {
		this.repository = repo;
	}
}
