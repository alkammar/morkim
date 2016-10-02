package lib.morkim.mfw.domain;

import java.util.Observable;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.repo.Repository;
import lib.morkim.mfw.repo.gateway.GatewayPersistException;
import lib.morkim.mfw.repo.gateway.GatewayRetrieveException;

public abstract class Model extends Observable {

	private MorkimApp morkimApp;
	private Repository repository;

	public Model(MorkimApp morkimApp) {

		this.morkimApp = morkimApp;
		setRepository(((MorkimApp) morkimApp).getRepo());
	}
	
	public abstract void load() throws GatewayRetrieveException ;
	public abstract void save() throws GatewayPersistException;

	public MorkimApp getAppContext() {
		return morkimApp;
	}

	public void setAppContext(MorkimApp morkimApp) {
		this.morkimApp = morkimApp;
	}

	public Repository getRepo() {
		return repository;
	}

	public void setRepository(Repository repo) {
		this.repository = repo;
	}
}
