package lib.morkim.examples.model;

import lib.morkim.examples.repo.ExampleGateway;
import lib.morkim.mfw.domain.DataGateway;
import lib.morkim.mfw.domain.Entity;

@DataGateway(ExampleGateway.class)
public class ExampleEntity extends Entity {

	public int index;
}
