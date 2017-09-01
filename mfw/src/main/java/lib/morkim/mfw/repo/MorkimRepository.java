package lib.morkim.mfw.repo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.DataGateway;
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

			onUpgrade(version, savedVersion);

			if (version == currentVersion) repoVersion.set(currentVersion);
		}
	}

	protected RepoVersion createVersionGateway() {
		return new RepoVersion(context);
	}

	@Override
	public <E extends Entity> Gateway<E> get(Class<E> entityClass) {

		Class<? extends AbstractGateway<E>> gatewayClass = null;

		DataGateway gatewayAnnotation = entityClass.getAnnotation(DataGateway.class);
		if (gatewayAnnotation != null)
			gatewayClass = (Class<? extends AbstractGateway<E>>) gatewayAnnotation.value();

		if (gatewayClass != null) {
			try {
				Constructor<?> constructor = gatewayClass.getDeclaredConstructor(MorkimApp.class);
				constructor.setAccessible(true);
				constructor.newInstance(context);

				return (Gateway<E>) constructor.newInstance(context);

			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		return new EmptyGateway<>(context);
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
	 * @param savedVersion
	 */
	protected abstract void onUpgrade(int toVersion, int savedVersion);
}
