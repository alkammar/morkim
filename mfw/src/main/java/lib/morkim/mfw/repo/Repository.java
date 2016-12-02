package lib.morkim.mfw.repo;

import lib.morkim.mfw.domain.Entity;
import lib.morkim.mfw.repo.gateway.Gateway;


public interface Repository {

	/**
	 * Returns a Gateway that is mapped to present a specific {@link Entity} class
	 * @param cls The Entity class that maps to a specific Gateway
	 * @param <T> The Entity class
	 * @return The Gateway that is mapped to the passed Entity
	 */
	<T extends Entity> Gateway<T> get(Class<T> cls);
}
