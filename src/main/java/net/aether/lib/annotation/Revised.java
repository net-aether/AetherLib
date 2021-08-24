package net.aether.lib.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that a type/method/constructor has been revised and a better alternative is available
 * 
 * @author Cheos
 */
@Since(V0_0_1)
@Retention(RUNTIME)
@Target({ TYPE, METHOD, CONSTRUCTOR })
public @interface Revised {
	/**
	 * @return The reason why the type/method/constructor has been revised
	 */
	String value() default "";
	/**
	 * @return A path to an alternative type/method/constructor to use instead of
	 */
	String alternative() default "";
	/**
	 * @return The (date) a revision for the type/method/constructor has been released
	 */
	String since() default "";
}
