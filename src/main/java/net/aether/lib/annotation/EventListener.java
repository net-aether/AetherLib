package net.aether.lib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.*;

@Retention(RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface EventListener {
	String test() default "";
}
