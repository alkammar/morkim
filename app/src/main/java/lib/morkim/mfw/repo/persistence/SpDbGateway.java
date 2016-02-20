package lib.morkim.mfw.repo.persistence;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Entity;
import lib.morkim.mfw.repo.Filter;
import lib.morkim.mfw.repo.SpRepo;
import lib.morkim.mfw.repo.gateway.AbstractGateway;
import lib.morkim.mfw.repo.gateway.GatewayPersistException;
import lib.morkim.mfw.repo.gateway.GatewayRetrieveException;

public abstract class SpDbGateway extends AbstractGateway {

	protected static final String SP_DEFAULT = "sp.default";
	
	private SpRepo spRepo;

	public SpDbGateway(MorkimApp morkimApp) {
		super(morkimApp);
		
		spRepo = new SpRepo(morkimApp.getContext());
	}

	@Override
	public void persist(Entity entity) throws GatewayPersistException {
		try {
			spRepo.write(source(), entity.getSysId(), serialize(entity));
		} catch (JSONException e) {
			throw new GatewayPersistException();
		}
	}

	@Override
	public Entity retrieve() {
		return null;
	}

	@Override
	public Entity retrieve(int source) {
		return null;
	}

	@Override
	public List<Entity> retrieveAll() throws GatewayRetrieveException {
		
		Map<String, ?> pairs = spRepo.read(source());
		
		List<Entity> list = new ArrayList<Entity>();
		for (String key : pairs.keySet())
			try {
				Entity entity = deserialize((String) pairs.get(key));
				entity.setSysId(UUID.fromString(key));
				list.add(entity);
			} catch (JSONException e) {
				throw new GatewayRetrieveException();
			}
		
		return list ;
	}

	@Override
	public List<Entity> retrieve(Filter filter) {
		return null;
	}

	@Override
	public void delete(Entity data) {
		spRepo.delete(source(), data.getSysId().toString());
	}

	protected abstract String serialize(Entity entity) throws JSONException;
	protected abstract Entity deserialize(String string) throws JSONException;

	protected long asLong(Object object) {
		return (object instanceof Integer) ? (Integer) object : (Long) object;
	}

	protected double asDouble(Object object) {
		return (object instanceof Integer) ? (Integer) object : (Double) object;
	}
}
