package lib.morkim.examples.usecase;


import lib.morkim.examples.app.ExampleApp;
import lib.morkim.examples.backendinterfaces.BackEndCall;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.repo.gateway.Gateway;
import lib.morkim.mfw.usecase.UseCaseDependencies;

public interface ExampleUseCaseDependencies extends UseCaseDependencies<ExampleApp, Model> {

	Gateway gateway();

	BackEndCall getBackEndCall();
}
