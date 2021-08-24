package net.aether.lib.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.CLASS;
import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import net.aether.lib.misc.AetherLibVersion;

/**
 * Indicates the version of AetherLib an object has been added.<br>
 * Please note that not everything may be annotated and that scoped annotations must be respected
 * (i.e. if a class is annotated, all methods count as annotated as well)<br><br>
 * This annotation is for use from within AetherLib only!
 * 
 * @author Cheos
 */
@Since(V0_0_1)
@Retention(CLASS)
@Target({ PACKAGE, TYPE, ANNOTATION_TYPE, FIELD, METHOD, CONSTRUCTOR })
public @interface Since {
	/**
	 * @return the date/release the annotated object has been added
	 */
	AetherLibVersion value();
}
