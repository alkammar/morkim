package lib.morkim.mfw.repo.gateway;

import lib.morkim.mfw.app.AppContext;

public abstract class AbstractGateway implements Gateway {
	
	protected AppContext appContext;
	
	public AbstractGateway(AppContext appContext) {
		this.appContext = appContext;
	}

	@Override
	public int getVersion() {
		return 0;
	}

	public String id() {
		return null;
	}
}
