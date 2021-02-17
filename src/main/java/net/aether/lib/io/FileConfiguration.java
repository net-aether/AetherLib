package net.aether.lib.io;

import java.io.File;
import java.io.IOException;

import net.aether.lib.exceptions.InvalidTypeException;

/**
 * Interface that allows access to any Aether file configurations.
 * @author Kilix
 *
 */
public interface FileConfiguration {

	// Load \\
	/**
	 * (re)loads the configuration from the supplied File
	 * @param file the file
	 * @throws IOException if an error occurs
	 */
	public void load(File file) throws IOException;
	/**
	 * Loads the configuration using the supplied String.<br>
	 * This could search for a file with the string's path, or allow for parsing values returned by {@link #asString()}.<br>
	 * Please look into the javadoc of the implementation.
	 * @param string a path as a string
	 */
	public void load(String string);
	
	// Save \\
	/**
	 * Saves the configuration to the supplied file.<br>
	 * Convenience method: calls save without prettyPrint.<br>
	 * <b>NOTE:</b> prettyPrint might not be supported, or make any difference.
	 * @param file the file to save to
	 * @throws IOException if an error occurs
	 */
	public default void save(File file) throws IOException { save(file, false); };
	/**
	 * Saves the configuration to the supplied file.<br>
	 * If implemented/supported, will save the configuration in a more easily readable Format when prettyPrint = true
	 * @param file the file to save to
	 * @param prettyPrint if the output should be formatted nice
	 * @throws IOException if an error occurs
	 */
	public void save(File file, boolean prettyPrint) throws IOException;
	/**
	 * Returns a String, containing all information of the configuration.<br>
	 * It is recommended, that {@link Object#toString() toString()} would return the exact same value, but this might not always be the case.<br>
	 * Please look into the javadoc of the implementation
	 * @return a string representation of this file
	 */
	public String asString();
	
	// Optional Values \\
	/**
	 * Checks if the file is supported for the type of configuration.<br>
	 * <span style="color: orangered">The exact implementation depends on the configuration type.</span><br>
	 * This could either just check the filename, location or even the contents.<br>
	 * Please look into the javadoc of the implementation<br><br>
	 * If not implemented, will default to true
	 * @param file the file to check
	 * @return true if the configuration supports the file, false otherwise.
	 */
	public default boolean isFileSupported(File file) { return true; }
	
	// Fallback Value \\
	/**
	 * Sets the fallback value of <span style="color:orange">path</span>, which will be used if the value is either not set or invalid.
	 * @param path the path to set a fallback value for
	 * @param fallback the fallback value
	 */
	public void setFallback(String path, String fallback);
	/**
	 * Gets the fallback value of <span style="color:orange">path</span>.<br>
	 * @param path the path to get the fallback value for
	 * @return the fallback value as a string, or null if no fallback value has been set
	 */
	public String getFallback(String path);
	
	// Type \\
	/**
	 * Sets the type of <span style="color:orange">path</span>.<br>
	 * If the FileConfiguration has a shorter String associated with the class, it would be used instead.
	 * @param path the path
	 * @param type the type
	 */
	public void setType(String path, Class<?> type);
	/**
	 * Sets the type of <span style="color:orange">path</span> to the supplied string.<br>
	 * This type can only be parsed correctly, if there is a class associated with this string.
	 * @param path the path
	 * @param type tha type
	 */
	public void setType(String path, String type);
	
	/**
	 * Gets the type of <span style="color:orange">path</span>.
	 * @param path the path
	 * @return the name of the type, or null if no type was specified
	 */
	public String getTypeName(String path);
	/**
	 * Gets the type of <span style="color:orange">path</span>.
	 * @param path the path
	 * @return the type as a class
	 * @throws ClassNotFoundException if the stored type string is not associated with any class.
	 */
	public Class<?> getType(String path) throws ClassNotFoundException;
	
	// Setter \\
	/**
	 * Sets the value of <span style="color:orange">path</span>.<br>
	 * If the value is of wrong type, will leave the path as it was before.
	 * @param path the path
	 * @param value the value
	 */
	public void set(String path, Object value);
	/**
	 * Sets the value of <span style="color:orange">path</span>.<br>
	 * If the value is of a wrong type, tries to parse it into the correct one.<br>
	 * If the value could not be parsed, tries to use the fallback value.<br>
	 * If there is no fallback value, will leave the path as it was before.
	 * @param path the path
	 * @param value the value
	 */
	public void trySet(String path, Object value);
	/**
	 * Sets the value of <span style="color:orange">path</span>.<br>
	 * If the value is of a wrong type, the type will be updated to the values type.
	 * @param path the path
	 * @param value the value
	 */
	public void forceSet(String path, Object value);
	
	// Comments \\
	/**
	 * Returns the comment of <span style="color:orange">path</span>.
	 * @param path the path
	 * @return The comment, or null if the path does not exist
	 */
	public String[] getComment(String path);
	/**
	 * Sets the comment of <span style="color:orange">path</span>.<br>
	 * If the comment is empty it will be removed completely.
	 * @param path the path
	 * @param comment the comment
	 */
	public void setComment(String path, String... comment);
	
	// Checks \\
	/**
	 * @return true if there is a a value set at the path, false otherwise
	 */
	public boolean isSet(String path);
	/**
	 * @return true if there is a fallback value set for that path, false otherwise
	 */
	public boolean hasFallback(String path);
	/**
	 * @return true if the value at the supplied path is a list
	 */
	public boolean isList(String path);
	
	// Getter \\
	/**
	 * Gets the value of <span style="color:orange">path</span>.<br>
	 * If the value has a set type, will try to parse to that type.<br>
	 * Otherwise will try to parse in the following order:<ul>
	 * <li>The specified class</li>
	 * <li>boolean</li>
	 * <li>integer</li>
	 * <li>long</li>
	 * <li>float</li>
	 * <li>double</li>
	 * <li>String</li>
	 * </ul>
	 * @param path the path
	 * @return The parsed object, or null if the value does not exist
	 */
	public Object get(String path);
	/**
	 * Gets the value of <span style="color:orange">path</span> as the supplied class.<br>
	 * @param path the path
	 * @param parseToClass the type to cast to
	 * @param <T> a type
	 * @return the parsed object
	 * @throws InvalidTypeException if the value could not be parsed to the supplied class.
	 */
	public <T> T get(String path, Class<? extends T> parseToClass) throws InvalidTypeException;
	
	// Standard parsed getter \\
	/**
	 * Gets the value of <span style="color:orange">path</span> as a boolean.<br>
	 * @param path the path
	 * @return the parsed boolean
	 * @throws InvalidTypeException if the value could not be parsed as boolean.
	 */
	public boolean 	getBoolean(String path) throws InvalidTypeException;
	/**
	 * Gets the value of <span style="color:orange">path</span> as an integer.<br>
	 * @param path the path
	 * @return the parsed integer
	 * @throws InvalidTypeException if the value could not be parsed as integer.
	 */
	public int 		getInt	  (String path) throws InvalidTypeException;
	/**
	 * Gets the value of <span style="color:orange">path</span> as a String.<br>
	 * @param path the path
	 * @return the parsed string
	 */
	public String 	getString (String path);
	/**
	 * Gets the value of <span style="color:orange">path</span> as a float.<br>
	 * @param path the path
	 * @return the parsed float
	 * @throws InvalidTypeException if the value could not be parsed as float.
	 */
	public float 	getFloat  (String path) throws InvalidTypeException;
	/**
	 * Gets the value of <span style="color:orange">path</span> as a double.<br>
	 * @param path the path
	 * @return the parsed double
	 * @throws InvalidTypeException if the value could not be parsed as double.
	 */
	public double	getDouble (String path) throws InvalidTypeException;
	/**
	 * Gets the value of <span style="color:orange">path</span> as a long.<br>
	 * @param path the path
	 * @return the parsed long
	 * @throws InvalidTypeException if the value could not be parsed as long.
	 */
	public long		getLong   (String path) throws InvalidTypeException;
	
}
