package lib.morkim.mfw.repo.persistence;

import java.util.List;
import java.util.Map;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.domain.Entity;
import lib.morkim.mfw.repo.Filter;
import lib.morkim.mfw.repo.SpRepo;
import lib.morkim.mfw.repo.gateway.AbstractGateway;
import lib.morkim.mfw.repo.gateway.GatewayRetrieveException;

public abstract class SpGateway extends AbstractGateway {

	protected static final String SP_DEFAULT = "sp.default";
	
	private SpRepo spRepo;

	public SpGateway(AppContext appContext) {
		super(appContext);
		
		spRepo = new SpRepo(appContext.getContext());
	}

	@Override
	public void persist(Entity entity) {
		spRepo.write(source(), mapValues(entity));
	}

	protected abstract Map<String, Object> mapValues(Entity entity);
	protected abstract Map<String, Object> keysAndDefaults();

	@Override
	public Entity retrieve() {
		return getEntity(spRepo.read(source(), keysAndDefaults()));
	}

	protected abstract Entity getEntity(Map<String, ?> map);
	protected abstract Entity createEntity();

	@Override
	public Entity retrieve(int id) {
		return null;
	}

	@Override
	public List<Entity> retrieveAll() throws GatewayRetrieveException {
		return null;
	}

	@Override
	public List<Entity> retrieve(Filter filter) {
		return null;
	}

	@Override
	public String source() {
		return SP_DEFAULT;
	}
}
