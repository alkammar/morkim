package lib.morkim.mfw.repo.gateway;

import java.util.List;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.domain.Entity;
import lib.morkim.mfw.repo.Filter;

public class EmptyGateway extends AbstractGateway {

	public EmptyGateway(AppContext appContext) {
		super(appContext);
	}

	@Override
	public void persist(Entity data) throws GatewayPersistException {
	}

	@Override
	public Entity retrieve() throws GatewayRetrieveException {
		return null;
	}

	@Override
	public Entity retrieve(int id) throws GatewayRetrieveException {
		return null;
	}

	@Override
	public List<Entity> retrieveAll() throws GatewayRetrieveException {
		return null;
	}

	@Override
	public List<Entity> retrieve(Filter filter) throws GatewayRetrieveException {
		return null;
	}

}