package lib.morkim.mfw.repo;

import lib.morkim.mfw.domain.Entity;
import lib.morkim.mfw.repo.gateway.Gateway;


public interface Repository {
	
	<T extends Entity> Gateway<T> get(Class<T> cls);
}
