package lib.morkim.mfw.repo.persistence;

import java.util.List;
import java.util.Map;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Entity;
import lib.morkim.mfw.repo.Filter;
import lib.morkim.mfw.repo.SpRepo;
import lib.morkim.mfw.repo.gateway.AbstractGateway;
import lib.morkim.mfw.repo.gateway.GatewayRetrieveException;

@SuppressWarnings("WeakerAccess")
public abstract class SpGateway<E extends Entity> extends AbstractGateway<E> {

	protected static final String SP_DEFAULT = "sp.default";
	
	private SpRepo spRepo;

	public SpGateway(MorkimApp morkimApp) {
		super(morkimApp);

		spRepo = new SpRepo(morkimApp.getContext());
	}

	@Override
	public void persist(E entity) {
		spRepo.write(source(), mapValues(entity));
	}

	protected abstract Map<String, Object> mapValues(E entity);
	protected abstract Map<String, Object> keysAndDefaults();

	@Override
	public E retrieve() {
		return getEntity(spRepo.read(source(), keysAndDefaults()));
	}

	protected abstract E getEntity(Map<String, ?> map);
	protected abstract E createEntity();

	@Override
	public E retrieve(int id) {
		return null;
	}

	@Override
	public List<E> retrieveAll() throws GatewayRetrieveException {
		return null;
	}

	@Override
	public List<E> retrieve(Filter filter) {
		return null;
	}

	@Override
	public void delete(E data) {

	}

	@Override
	public String source() {
		return SP_DEFAULT;
	}
}
