package lib.morkim.mfw.repo.gateway;

import java.util.List;

import lib.morkim.mfw.domain.Entity;
import lib.morkim.mfw.repo.Filter;


public interface Gateway {

	public static final int ERROR_DATA_TYPE_NOT_FOUND = -101;
	
	public void persist(Entity data) throws GatewayPersistException;
	
	public Entity retrieve() throws GatewayRetrieveException;
	public Entity retrieve(int id) throws GatewayRetrieveException;
	public List<Entity> retrieveAll() throws GatewayRetrieveException;
	public List<Entity> retrieve(Filter filter) throws GatewayRetrieveException;

	public void delete(Entity data);

	public int getVersion();
}
