package lib.morkim.examples.screen;

import lib.morkim.examples.app.ExampleApp;
import lib.morkim.examples.backendimpl.BackEndCallImpl;
import lib.morkim.examples.backendinterfaces.BackEndCall;
import lib.morkim.examples.repo.ExampleGateway;
import lib.morkim.examples.usecase.ExampleUseCaseDependencies;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.repo.gateway.Gateway;

class ExampleUseCaseDependenciesImpl implements ExampleUseCaseDependencies {

    private ExampleApp context;

    ExampleUseCaseDependenciesImpl(ExampleApp context) {
        this.context = context;
    }

    @Override
    public ExampleApp getContext() {
        return context;
    }

    @Override
    public Model getModel() {
        return context.getModel();
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
