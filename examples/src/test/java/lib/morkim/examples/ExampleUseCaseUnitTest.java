package lib.morkim.examples;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import lib.morkim.examples.app.ExampleApp;
import lib.morkim.examples.backendinterfaces.BackEndCall;
import lib.morkim.examples.task.ExampleTask;
import lib.morkim.examples.task.ExampleTaskDependencies;
import lib.morkim.mfw.domain.Entity;
import lib.morkim.mfw.repo.Filter;
import lib.morkim.mfw.repo.gateway.Gateway;
import lib.morkim.mfw.repo.gateway.GatewayPersistException;
import lib.morkim.mfw.repo.gateway.GatewayRetrieveException;
import lib.morkim.mfw.usecase.TaskDependencies;
import lib.morkim.mfw.usecase.UseCaseCreator;

public class ExampleUseCaseUnitTest {

    private ExampleTask exampleTask;

    @Before
    public void setUp() {

        exampleTask = new UseCaseCreator<ExampleTask>()
                .create(ExampleTask.class)
                .with(exampleUseCaseDependencies);
    }

    @Test
    public void addition_isCorrect() throws Exception {

        exampleTask.execute();

//        assertEquals(4, 2 + 2);
    }

    private TaskDependencies exampleUseCaseDependencies = new ExampleTaskDependencies() {

        @Override
        public ExampleApp getContext() {
            return null;
        }

        @Override
        public Gateway gateway() {
            return new Gateway() {
                @Override
                public void persist(Entity data) throws GatewayPersistException {

                }

                @Override
                public Entity retrieve() throws GatewayRetrieveException {
                    return null;
                }

                @Override
                public Entity retrieve(int id) throws GatewayRetrieveException {
                    return null;
                }

                @Override
                public List retrieveAll() throws GatewayRetrieveException {
                    return null;
                }

                @Override
                public List retrieve(Filter filter) throws GatewayRetrieveException {
                    return null;
                }

                @Override
                public void delete(Entity data) {

                }

                @Override
                public int getVersion() {
                    return 0;
                }
            };
        }

        @Override
        public BackEndCall getBackEndCall() {
            return new BackEndCall() {
                @Override
                public void send(Request request) {

                }
            };
        }
    };
}