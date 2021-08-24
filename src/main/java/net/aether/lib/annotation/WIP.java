package net.aether.lib.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that a package/type/method/constructor is being worked on and not in a finished state
 * 
 * @author Cheos
 */
@Since(V0_0_1)
@Retention(RUNTIME)
@Target({ PACKAGE, TYPE, METHOD, CONSTRUCTOR })
public @interface WIP {
	/**
	 * @return The reason why this is being worked on/what the intention behind the finished product is
	 */
	String value()   default "";
	/**
	 * @return The reason why this is being worked on/what the intention behind the finished product is
	 */
	String reason()  default "";
	/**
	 * @return The (date), work on the package/type/method/constructor being worked on commenced
	 */
	String since()   default "";
	/**
	 * @return The scheduled release (date) of the package/type/method/constructor being worked on
	 */
	String release() default "";
}
