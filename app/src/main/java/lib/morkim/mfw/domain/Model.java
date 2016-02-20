package lib.morkim.mfw.domain;

import java.util.Observable;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.repo.Repository;
import lib.morkim.mfw.repo.gateway.GatewayPersistException;
import lib.morkim.mfw.repo.gateway.GatewayRetrieveException;

public abstract class Model extends Observable {

	private MorkimApp appContext;
	private Repository repository;

	public Model(MorkimApp morkimApp) {

		this.appContext = morkimApp;
		setRepository(((MorkimApp) morkimApp).getRepos());
	}
	
	public abstract void load() throws GatewayRetrieveException ;
	public abstract void save() throws GatewayPersistException;

	public MorkimApp getAppContext() {
		return appContext;
	}

	public void setAppContext(MorkimApp appContext) {
		this.appContext = appContext;
	}

	public Repository getRepo() {
		return repository;
	}

	public void setRepository(Repository repo) {
		this.repository = repo;
	}
}
