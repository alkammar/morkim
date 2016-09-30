package lib.morkim.examples;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.repo.MorkimRepository;

class ExampleRepository extends MorkimRepository {

	ExampleRepository(MorkimApp application) {
		super(application);
	}

	@Override
	protected int version() {
		return 0;
	}

	@Override
	protected void onUpgrade(int toVersion) {

	}
}
