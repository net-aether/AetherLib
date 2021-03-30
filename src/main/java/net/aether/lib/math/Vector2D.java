package net.aether.lib.math;

import net.aether.lib.data.Pair;

/**
 * Represents a Vector with 2 components/dimensions (X, Y)
 * 
 * @author Kilix
 */
public class Vector2D implements IVector {
	
	protected Vector2D(Number x, Number y) {
		this.x = x.doubleValue();
		this.y = y.doubleValue();
	}
	
	protected final double x, y;
	
	public Number getMagnitude() { return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)); }
	public Number get2DAngle() { return Math.atan2(y, x); }
	public Pair<Number, Number> get3DAngles() { return Pair.of(get2DAngle(), 0); }
	public Number[] get4DAngles() { return new Number[] {get2DAngle(), 0, 0}; }
	public Number[] getNAngles(int n) { return new Number[n]; }
	
	public int getComponentCount() { return 2; }
	public Number get(int n) {
		switch (n) {
			case 0:  return x;
			case 1:  return y;
			default: return 0;
		}
	}
	
	public Vector2D add(IVector vector) 	 	{ return new Vector2D(x + vector.getX().doubleValue(), y + vector.getY().doubleValue()); }
	public Vector2D subtract(IVector vector) 	{ return new Vector2D(x - vector.getX().doubleValue(), y - vector.getY().doubleValue()); }
	public Vector2D multiply(IVector vector) 	{ return new Vector2D(x * vector.getX().doubleValue(), y * vector.getY().doubleValue()); }
	public Vector2D divide(IVector vector) 	 	{ return new Vector2D(x / vector.getX().doubleValue(), y / vector.getY().doubleValue()); }
	public Vector2D multiply(Number multiplier) { return new Vector2D(x * multiplier.doubleValue(), y * multiplier.doubleValue()); }
		
	public Vector2D set(int n, Number num) { return new Vector2D(n == 0 ? num : x, n == 1 ? num : y); }
	public Vector2D copy() { return new Vector2D(x, y); }
	
	public static Vector2D cast(IVector vector) { return new Vector2D(vector.getX(), vector.getY()); }
	public static Vector2D from2DComponents(Number x, Number y) { return new Vector2D(x, y); }
	public static Vector2D from2DPolar(Number magnitude, Number angle) { return new Vector2D(magnitude.doubleValue() * Math.cos(angle.doubleValue()), magnitude.doubleValue() * Math.sin(angle.doubleValue())); }
}
