package lib.morkim.examples;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.repo.MorkimRepository;
import lib.morkim.mfw.repo.gateway.GatewayPersistException;
import lib.morkim.mfw.repo.gateway.GatewayRetrieveException;
import lib.morkim.mfw.task.TaskFactory;

public class ExampleApp extends MorkimApp<Model, MorkimRepository> {

    @Override
    protected void createFactories() {

    }

    @Override
    protected MorkimRepository createRepo() {
        return new ExampleRepository(this);
    }

    @Override
    protected Model createModel() {
        return new Model(this) {
            @Override
            public void load() throws GatewayRetrieveException {

            }

            @Override
            public void save() throws GatewayPersistException {

            }
        };
    }

    @Override
    protected TaskFactory createScheduledTaskFactory() {
        return new ExampleTaskFactory(this);
    }
}
