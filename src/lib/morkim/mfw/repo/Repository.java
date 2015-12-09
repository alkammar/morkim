package lib.morkim.mfw.repo;

import lib.morkim.mfw.repo.gateway.Gateway;


public interface Repository {
	
	public Gateway get(Class<?> cls);
}
