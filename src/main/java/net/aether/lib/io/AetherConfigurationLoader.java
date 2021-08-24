package net.aether.lib.io;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import java.io.File;

import net.aether.lib.annotation.Since;
import net.aether.lib.data.DoubleHashMap;

/**
 * This is not a proper loader.<br>
 * This class stores pairs of Strings and classnames, which are passed down to any "loaded" configuration.<br>
 * These pairs determine which strings should be stores as types<br>
 * <br>
 * e.g. myPair[{@link net.aether.lib.data.Pair}]: {@code <myValue>} could be shortened to myPair[Pair]: {@code <myValue>}
 * 
 * @author Kilix
 *
 */
@Since(V0_0_1)
public class AetherConfigurationLoader {

	/**
	 * Contains all the predefined types by the 'system'
	 */
	private static final DoubleHashMap<String, String> systemTypes = new DoubleHashMap<>();
	/*
	 * Register all the predefined types
	 */
	static {
		systemTypes.put("int", 		Integer.class.getName());
		systemTypes.put("long", 	Long.class.getName());
		systemTypes.put("float", 	Float.class.getName());
		systemTypes.put("double", 	Double.class.getName());
		systemTypes.put("str", 		String.class.getName());
	}
	
	/**
	 * Contains all the user-defined types
	 */
	private DoubleHashMap<String, String> knownTypes = new DoubleHashMap<>();

	/**
	 * Registers the supplied class as a known type
	 * @param typeName
	 * @param clazz
	 */
	public void registerType(String typeName, Class<?> clazz) {
		try {
			registerType(typeName, clazz.getName());
		} catch (ClassNotFoundException e) {} // Ignore CNFE, because we use the class to get it's name. If it could not be found we would have bigger problems
	}
	/**
	 * Registers the supplied classname as a known type
	 * @param name
	 * @param className
	 * @throws ClassNotFoundException if there is no class with the supplied name
	 */
	public void registerType(String name, String className) throws ClassNotFoundException {
		knownTypes.put(name, className);
	}
	
	/**
	 * Returns the class registered to the supplied type name.
	 * @param typeName
	 * @return
	 * @throws ClassNotFoundException if (for some reason) the class could not be found by the classloader
	 * @throws TypeNotPresentException if the supplied type name is not registered
	 */
	public Class<?> getType(String typeName) throws ClassNotFoundException, TypeNotPresentException {
		if (knownTypes.containsKey(typeName)) return Class.forName(knownTypes.get(typeName));
		else throw new TypeNotPresentException(typeName, new NullPointerException(typeName + " not registered."));
	}
	
	
	public AetherConfiguration createEmpty() {
		return new AetherConfiguration(this);
	}
	public AetherConfiguration loadFile(File file) {
		return new AetherConfiguration(this);
	}
}
