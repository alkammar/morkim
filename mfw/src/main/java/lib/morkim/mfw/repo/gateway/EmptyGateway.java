package lib.morkim.mfw.repo.gateway;

import java.util.List;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Entity;
import lib.morkim.mfw.repo.Filter;

public class EmptyGateway<E extends Entity> extends AbstractGateway<E> {

	public EmptyGateway(MorkimApp morkimApp) {
		super(morkimApp);
	}

	@Override
	public void persist(Entity data) throws GatewayPersistException {
	}

	@Override
	public E retrieve() throws GatewayRetrieveException {
		return null;
	}

	@Override
	public E retrieve(int id) throws GatewayRetrieveException {
		return null;
	}

	@Override
	public List<E> retrieveAll() throws GatewayRetrieveException {
		return null;
	}

	@Override
	public List<E> retrieve(Filter filter) throws GatewayRetrieveException {
		return null;
	}

	@Override
	public void deleteAll() {

	}

	@Override
	public void delete(Entity data) {

	}

}
