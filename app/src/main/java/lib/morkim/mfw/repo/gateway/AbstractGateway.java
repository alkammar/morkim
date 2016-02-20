package lib.morkim.mfw.repo.gateway;

import lib.morkim.mfw.app.MorkimApp;

public abstract class AbstractGateway implements Gateway {
	
	protected MorkimApp morkimApp;
	
	public AbstractGateway(MorkimApp morkimApp) {
		this.morkimApp = morkimApp;
	}

	@Override
	public int getVersion() {
		return 0;
	}

	public String source() {
		return null;
	}
}
