package lib.morkim.mfw.repo;

import lib.morkim.mfw.app.MorkimApp;
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
	public Gateway get(int gatewayId) {
		return createGateway(gatewayId);
	}

	protected Gateway createGateway(int gatewayId) {
		return new EmptyGateway(context);
	}

	protected abstract void onUpgrade(int toVersion);
}
