package lib.morkim.examples;

import lib.morkim.examples.app.ExampleApp;
import lib.morkim.examples.backendinterfaces.BackEndCall;
import lib.morkim.examples.usecase.ExampleUseCaseDependencies;
import lib.morkim.mfw.repo.gateway.Gateway;

class TestExampleUseCaseDependencies implements ExampleUseCaseDependencies {

    private ExampleApp context;

    public TestExampleUseCaseDependencies(ExampleApp context) {
        this.context = context;
    }

    @Override
    public ExampleApp getContext() {
        return context;
    }

    @Override
    public Gateway gateway() {
        return null;
    }

    @Override
    public BackEndCall getBackEndCall() {
        return null;
    }
}
