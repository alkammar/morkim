package lib.morkim.examples;

import java.util.Map;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.repo.persistence.SpGateway;

class ExampleGateway extends SpGateway<ExampleEntity> {

    public ExampleGateway(MorkimApp morkimApp) {
        super(morkimApp);
    }

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
