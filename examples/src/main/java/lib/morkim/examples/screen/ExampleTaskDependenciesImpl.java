package lib.morkim.examples.screen;

import lib.morkim.examples.app.ExampleApp;
import lib.morkim.examples.backendimpl.BackEndCallImpl;
import lib.morkim.examples.backendinterfaces.BackEndCall;
import lib.morkim.examples.repo.ExampleGateway;
import lib.morkim.examples.usecase.ExampleTaskDependencies;
import lib.morkim.mfw.repo.gateway.Gateway;

class ExampleTaskDependenciesImpl implements ExampleTaskDependencies {

    private ExampleApp context;

    ExampleTaskDependenciesImpl(ExampleApp context) {
        this.context = context;
    }

    @Override
    public ExampleApp getContext() {
        return context;
    }

    @Override
    public Gateway gateway() {
        return new ExampleGateway(context);
    }

    @Override
    public BackEndCall getBackEndCall() {
        return new BackEndCallImpl();
    }
}
