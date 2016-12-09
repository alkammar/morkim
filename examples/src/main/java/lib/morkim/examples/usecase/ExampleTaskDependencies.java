package lib.morkim.examples.usecase;


import lib.morkim.examples.app.ExampleApp;
import lib.morkim.examples.backendinterfaces.BackEndCall;
import lib.morkim.mfw.repo.gateway.Gateway;
import lib.morkim.mfw.usecase.TaskDependencies;

public interface ExampleTaskDependencies extends TaskDependencies<ExampleApp, ExampleTask> {

	Gateway gateway();

	BackEndCall getBackEndCall();
}
