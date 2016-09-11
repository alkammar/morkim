package lib.morkim.mfw.repo;

import java.util.HashMap;
import java.util.Map;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Entity;
import lib.morkim.mfw.repo.gateway.AbstractGateway;
import lib.morkim.mfw.repo.gateway.EmptyGateway;
import lib.morkim.mfw.repo.gateway.Gateway;

public abstract class MorkimRepository implements Repository {

	protected MorkimApp context;
	protected Map<Class<?>, Class<? extends AbstractGateway<? extends Entity>>> map;

	protected abstract int version();

	public MorkimRepository(MorkimApp application) {
		this.context = application;

		map = new HashMap<>();
		mapGateways(map);

		RepoVersion repoVersion = createVersionGateway();
		int savedVersion = repoVersion.get();

		int currentVersion = version();
		for (int version = savedVersion + 1; version <= currentVersion; version++) {

			onUpgrade(currentVersion);

			if (version == currentVersion) repoVersion.set(currentVersion);
		}
	}

	/**
	 * Override this method to associate a specific {@link Entity} to a specific {@link Gateway}.
	 * Gateway translate application data models from/to their persisted form.
	 * @param map The map to add entity/gateway pairs
	 */
	protected abstract void mapGateways(Map<Class<?>, Class<? extends AbstractGateway<? extends Entity>>> map);

	protected RepoVersion createVersionGateway() {
		return new RepoVersion(context);
	}

	@Override
	public <E extends Entity> Gateway<E> get(Class<E> entityClass) {

		Class<? extends AbstractGateway<E>> gatewayClass = (Class<? extends AbstractGateway<E>>) map.get(entityClass);

		if (gatewayClass != null) {
			try {
				AbstractGateway<E> gateway = gatewayClass.<E>newInstance();
				gateway.setMorkimApp(context);
				return gateway;
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return new EmptyGateway<>();
	}

	/**
	 * Checks for data persistence upgrades. Use the toVersion parameter to handle
	 * the upgrade to a specific version. This method will be called n number of times
	 * depending on the difference between the old repository version number and the
	 * latest repository version number.
	 * So if old version number is 3 and the latest version number is 6 then this method will
	 * be called 3 times with progressive {@code toVersion} parameter.
	 * This will basically hold your repository change history.
	 * Remember that the latest database version is determined by
	 * overriding the {@link #version()} method and returning the latest version number.
	 * @param toVersion The version to upgrade to
	 */
	protected abstract void onUpgrade(int toVersion);
}
