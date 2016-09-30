package lib.morkim.examples;

import lib.morkim.mfw.domain.DataGateway;
import lib.morkim.mfw.domain.Entity;

@DataGateway(ExampleGateway.class)
class ExampleEntity extends Entity {

	int index;
}
