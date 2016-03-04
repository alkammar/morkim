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

	protected Class<? extends AbstractGateway> getGatewayClass(Class<?> cls) {
		return EmptyGateway.class;
	}

	protected abstract void onUpgrade(int toVersion);
}
