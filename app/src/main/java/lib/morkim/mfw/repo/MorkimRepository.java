package lib.morkim.mfw.repo;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Entity;
import lib.morkim.mfw.repo.gateway.AbstractGateway;
import lib.morkim.mfw.repo.gateway.EmptyGateway;
import lib.morkim.mfw.repo.gateway.Gateway;

public abstract class MorkimRepository implements Repository {

	protected MorkimApp context;

	protected abstract int version();

	public MorkimRepository(MorkimApp application) {
		this.context = application;

		RepoVersion repoVersion = createVersionGateway();
		int savedVersion = repoVersion.get();

		int currentVersion = version();
		for (int version = savedVersion + 1; version <= currentVersion; version++) {

			onUpgrade(currentVersion);

			if (version == currentVersion) repoVersion.set(currentVersion);
		}
	}

	protected RepoVersion createVersionGateway() {
		return new RepoVersion(context);
	}

	@Override
	public <T extends Entity> Gateway<T> get(Class<T> cls) {
		return createGateway(cls);
	}

	protected <T extends Entity> Gateway<T> createGateway(Class<T> entityClass) {

		Class gatewayClass = getGatewayClass(entityClass);

		try {
			AbstractGateway<T> gateway = (AbstractGateway<T>) gatewayClass.newInstance();
			gateway.setMorkimApp(context);
			return gateway;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return new EmptyGateway<>();
	}

	/**
	 * Override this method to associate a specific {@link Entity} to a specific {@link Gateway}.
	 * Gateway translate application data models from/to their persisted form.
	 * @param cls The Entity that maps a Gateway for it
	 * @return The Gateway class
	 */
	protected Class<? extends AbstractGateway> getGatewayClass(Class<?> cls) {
		return EmptyGateway.class;
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
