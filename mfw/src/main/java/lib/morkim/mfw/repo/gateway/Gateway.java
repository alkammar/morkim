package lib.morkim.mfw.repo.gateway;

import java.util.List;

import lib.morkim.mfw.domain.Entity;
import lib.morkim.mfw.repo.Filter;


public interface Gateway<E extends Entity> {

	public static final int ERROR_DATA_TYPE_NOT_FOUND = -101;
	
	public void persist(E data) throws GatewayPersistException;
	
	public E retrieve() throws GatewayRetrieveException;
	public E retrieve(int id) throws GatewayRetrieveException;
	public List<E> retrieveAll() throws GatewayRetrieveException;
	public List<E> retrieve(Filter filter) throws GatewayRetrieveException;

	public void delete(E data);

	public int getVersion();
}
