package lib.morkim.examples;

import java.util.Map;

import lib.morkim.mfw.repo.persistence.SpGateway;

/**
 * Created by Kammar on 2/25/2016.
 */
public class ExampleGateway extends SpGateway<ExampleEntity> {

    @Override
    protected Map<String, Object> mapValues(ExampleEntity entity) {
        return null;
    }

    @Override
    protected Map<String, Object> keysAndDefaults() {
        return null;
    }

    @Override
    protected ExampleEntity getEntity(Map<String, ?> map) {
        return null;
    }

    @Override
    protected ExampleEntity createEntity() {
        return null;
    }
}
