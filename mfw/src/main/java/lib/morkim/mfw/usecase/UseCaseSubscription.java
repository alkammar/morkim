package lib.morkim.mfw.usecase;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface UseCaseSubscription {

	Class<? extends MorkimTask> [] value();
}
