package net.aether.lib.math;

import net.aether.lib.data.Pair;

/**
 * Allows you to (more or less) easily interact with Vectors
 * 
 * @author Kilix
 * @since 0.0.1-15-SNAPSHOT
 */
public interface IVector {
	
	/**
	 * Gets the magnitude (= length) of the vector.
	 * @return the magnitude as a {@link Number} object
	 */
	public Number getMagnitude();
	/**
	 * Gets the 2 dimensional angle, by using the 'x' and 'y' components with atan2
	 * @return the angle as a {@link Number} object
	 */
	public Number get2DAngle();
	/**
	 * Gets the 3 dimensional angles
	 * @return the angles as a Pair of 2 Number objects
	 */
	public Pair<Number, Number> get3DAngles();
	/**
	 * Gets the 4 dimensional angles
	 * @return the angles in a {@link Number} Array with a length of 3
	 */
	public Number[] get4DAngles();
	/**
	 * Gets <b>n</b> angles of the Vector, negative numbers will result in undefined behavior.
	 * @return
	 */
	public Number[] getNAngles(int n);
	/**
	 * @return a copy of the Vector, with the same angle(s), and a magnitude of 1
	 */
	public default IVector getUnitVector() { return multiply(1.0 / getMagnitude().doubleValue()); }
	
	/**
	 * Gets the component representing the first dimension
	 * @return a Number object representing the 'x' component
	 */
	public default Number getX() { return get(0); }
	/**
	 * Gets the component representing the second dimension
	 * @return a Number object representing the 'y' component
	 */
	public default Number getY() { return get(1); }
	/**
	 * Gets the component representing the third dimension
	 * @return a Number object representing the 'z' component
	 * @apiNote mathematically if a vector does not have a specific component, it still counts as 0, so that's what will be returned in that case
	 */
	public default Number getZ() { return get(2); }
	/**
	 * Gets the component representing the fourth dimension
	 * @return a Number object representing the 'w' component
	 * @apiNote mathematically if a vector does not have a specific component, it still counts as 0, so that's what will be returned in that case
	 */
	public default Number getW() { return get(3); }
	/**
	 * Gets the component representing the <b>n</b><span style="font-size: 0.8em; vertical-align: top">th</span> dimension
	 * @param n : specifies which component to target. Negative values are invalid, and suppling one will result in undefined behavior
	 * @return a Number object representing the 'specified' component
	 * @apiNote mathematically if a vector does not have a specific component, it still counts as 0, so that's what will be returned in that case
	 */
	public Number get(int n);
	/**
	 * Gets the component count of the vector
	 * @return how many components a vector has.
	 */
	public int getComponentCount();
	
	/**
	 * @param vector
	 * @return a new vector with the component values of the components added to the components of the original vector
	 */
	public IVector add(IVector vector);
	/**
	 * @param vector
	 * @return a new vector with the component values of the components subtracted to the components of the original vector
	 */
	public IVector subtract(IVector vector);
	/**
	 * @param vector
	 * @return a new vector with the component values of the original vector multiplied by the multiplier
	 */
	public IVector multiply(Number multiplier);
	/**
	 * @param vector
	 * @return a new vector with the component values of the components multiplied to the components of the original vector
	 */
	public IVector multiply(IVector vector);
	/**
	 * @param vector
	 * @return a new vector with the component values of the components divided to the components of the original vector
	 */
	public IVector divide(IVector vector);
		
	/**
	 * Sets the component representing the first dimension<br>
	 * Convenience Method for calling {@link #set(int, Number) set(0, x)}
	 * @return a copy of the Vector, with the modified value
	 */
	public default IVector setX(Number x) 		{ return set(0, x); }
	/**
	 * Sets the component representing the second dimension<br>
	 * Convenience Method for calling {@link #set(int, Number) set(1, y)}
	 * @return a copy of the Vector, with the modified value
	 */
	public default IVector setY(Number y) 		{ return set(1, y); }
	/**
	 * Sets the component representing the third dimension<br>
	 * Convenience Method for calling {@link #set(int, Number) set(2, z)}
	 * @return a copy of the Vector, with the modified value
	 */
	public default IVector setZ(Number z) 		{ return set(2, z); }
	/**
	 * Sets the component representing the fourth dimension<br>
	 * Convenience Method for calling {@link #set(int, Number) set(3, w)}
	 * @return a copy of the Vector, with the modified value
	 */
	public default IVector setW(Number w) 		{ return set(3, w); }
	/**
	 * Sets the component representing the <b>n</b><span style="font-size: 0.8em; vertical-align: top">th</span> dimension
	 * @param n : specifies which component to target. Negative values are invalid, and suppling one will result in undefined behavior
	 * @return a copy of the Vector, with the modified value
	 * @apiNote if not implemented, will return the Vector instance it was called on!
	 */
	public default IVector set(int n, Number num) 	{ return this; }
	
	/**
	 * @return a new Vector object with the same components
	 */
	public IVector copy();
	
}
