package lib.morkim.mfw.domain;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.repo.gateway.GatewayPersistException;
import lib.morkim.mfw.repo.gateway.GatewayRetrieveException;

public class EmptyModel extends Model {

	public EmptyModel(MorkimApp morkimApp) {
		super(morkimApp);
	}

	@Override
	public void load() throws GatewayRetrieveException {

	}

	@Override
	public void save() throws GatewayPersistException {

	}
}
