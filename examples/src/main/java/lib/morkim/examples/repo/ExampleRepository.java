package lib.morkim.examples.repo;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.repo.MorkimRepository;

public class ExampleRepository extends MorkimRepository {

	public ExampleRepository(MorkimApp application) {
		super(application);
	}

	@Override
	protected int version() {
		return 0;
	}

	@Override
	protected void onUpgrade(int toVersion, int savedVersion) {

	}
}
