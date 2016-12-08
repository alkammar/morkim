package lib.morkim.examples.backendimpl;

import dagger.Module;
import dagger.Provides;
import lib.morkim.examples.backendinterfaces.BackEndCall;


@Module
public class BackEndModule {

	@Provides
	BackEndCall providesBackEndCall() {
		return new BackEndCallImpl();
	}
}
