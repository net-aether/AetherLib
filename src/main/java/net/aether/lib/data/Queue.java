package net.aether.lib.data;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import net.aether.lib.annotation.Since;

/**
 * Defines the default functionality for standard Queue classes<br>
 * Go to {@link SimpleQueue} for the default implementation
 * 
 * @author Cheos
 */
@Since(V0_0_1)
public interface Queue<T> {
	/**
	 * Inserts a new element at index <b>idx</b>
	 * 
	 * @param t
	 * @param idx
	 * @return True if the element was inserted successfully, otherwise false
	 */
	boolean insert(T t, int idx);
	
	/**
	 * Pushes a new element to the end of the queue
	 * 
	 * @param t
	 * @return True if the element was appended successfully, otherwise false
	 */
	boolean push(T t);
	
	/**
	 * Pops the lowermost element off the queue and return it
	 * 
	 * @return The lowermost element of the queue
	 */
	T pop();
	
	/**
	 * Gets an element by index of the queue without impacting the queue directly
	 * 
	 * @param idx
	 * @return The element at <b>idx</b> in the queue or null, if <b>idx</b> is out of bounds
	 */
	T get(int idx);
	
	/**
	 * Removes an element by index from the queue
	 * 
	 * @param idx
	 * @return The element at <b>idx</b> in the queue or null, if <b>idx</b> is out of bounds, indicating an unsuccessful operation
	 */
	T remove(int idx);
	
	/**
	 * Removes an element from the queue
	 * 
	 * @param t
	 * @return The element itself in the queue or null, if the queue does not contain <b>t</b>, indicating an unsuccessful operation
	 */
	T remove(T t);
}
