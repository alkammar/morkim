package lib.morkim.examples;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.repo.MorkimRepository;
import lib.morkim.mfw.repo.gateway.GatewayPersistException;
import lib.morkim.mfw.repo.gateway.GatewayRetrieveException;
import lib.morkim.mfw.task.TaskFactory;

/**
 * Created by Kammar on 2/19/2016.
 */
public class ExampleApp extends MorkimApp {

    @Override
    protected void createFactories() {

    }

    @Override
    protected MorkimRepository createRepo() {
        return new MorkimRepository(this) {
            @Override
            protected int version() {
                return 0;
            }

            @Override
            protected void onUpgrade(int toVersion) {

            }
        };
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
        return null;
    }
}
