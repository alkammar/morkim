package lib.morkim.examples.backendimpl;


import dagger.Component;
import lib.morkim.examples.backendinterfaces.BackEndCall;
import lib.morkim.examples.usecase.ExampleTask;

@Component(modules = {BackEndModule.class})
public interface BackEndComponent {

	void inject(ExampleTask task);

	BackEndCall getBackEndCall();
}
