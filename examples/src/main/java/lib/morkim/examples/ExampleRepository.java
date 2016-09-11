package lib.morkim.examples;

import java.util.Map;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Entity;
import lib.morkim.mfw.repo.MorkimRepository;
import lib.morkim.mfw.repo.gateway.AbstractGateway;

/**
 * Created by Kammar on 3/4/2016.
 */
public class ExampleRepository extends MorkimRepository {

	public ExampleRepository(MorkimApp application) {
		super(application);
	}

	@Override
	protected int version() {
		return 0;
	}

	@Override
	protected void onUpgrade(int toVersion) {

	}

	@Override
	protected void mapGateways(Map<Class<?>, Class<? extends AbstractGateway<? extends Entity>>> map) {

		map.put(ExampleEntity.class, ExampleGateway.class);
	}

//	@Override
//	protected Class<? extends AbstractGateway> getGatewayClass(Class<?> cls) {
//
//		return map.get(cls);
//
//		if (ExampleEntity.class.equals(cls))
//			return ExampleGateway.class;
//
//		return super.getGatewayClass(cls);
//	}
}
