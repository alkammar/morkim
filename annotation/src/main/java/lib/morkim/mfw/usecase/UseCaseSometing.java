package lib.morkim.mfw.usecase;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // on class level
@Retention(RetentionPolicy.SOURCE)
public @interface UseCaseSometing {}
