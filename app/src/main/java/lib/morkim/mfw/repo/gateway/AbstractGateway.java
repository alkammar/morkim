package lib.morkim.mfw.repo.gateway;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Entity;

public abstract class AbstractGateway<E extends Entity> implements Gateway<E> {

	protected MorkimApp morkimApp;
	
	public AbstractGateway(MorkimApp morkimApp) {
		this.morkimApp = morkimApp;
	}

	public AbstractGateway() {

	}

	public void setMorkimApp(MorkimApp morkimApp) {
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
