package lib.morkim.mfw.repo.gateway;

import java.util.List;

import lib.morkim.mfw.domain.Entity;
import lib.morkim.mfw.repo.Filter;


public interface Gateway<E extends Entity> {

	int ERROR_DATA_TYPE_NOT_FOUND = -101;
	
	void persist(E data) throws GatewayPersistException;
	
	E retrieve() throws GatewayRetrieveException;
	E retrieve(int id) throws GatewayRetrieveException;
	List<E> retrieveAll() throws GatewayRetrieveException;
	List<E> retrieve(Filter filter) throws GatewayRetrieveException;

	void delete(E data);
	void deleteAll();

	int getVersion();
}
