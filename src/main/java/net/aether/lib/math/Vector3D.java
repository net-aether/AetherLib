package net.aether.lib.math;

import net.aether.lib.data.Pair;

/**
* Represents a Vector with 3 components/dimensions (X, Y, Z)
* 
* @author Kilix
*/
public class Vector3D implements IVector {
	
	protected Vector3D(Number x, Number y, Number z) {
		this.x = x.doubleValue();
		this.y = y.doubleValue();
		this.z = z.doubleValue();
	}
	
	protected final double x, y, z;
	
	public Number getMagnitude() {
		return Math.sqrt(
			Math.pow(x, 2) +
			Math.pow(y, 2) +
			Math.pow(z, 2)
		);
	}
	public Number get2DAngle() { return Math.atan2(y, x); }
	public Pair<Number, Number> get3DAngles() { return Pair.of(Math.atan2(y, x), Math.atan2(z, x)); }
	public Number[] get4DAngles() { return new Number[] { Math.atan2(y, x), Math.atan2(z, x), 0}; }
	public Number[] getNAngles(int n) {
		if (n < 0) return new Number[0];
		
		Number[] out = new Number[n];
		switch(n) {
			case 3:
				out[2] = z;
			case 2:
				out[1] = y;
			case 1:
				out[0] = x;
		}
		
		return out;
	}
	
	public int getComponentCount() { return 3; }
	public Number get(int n) {
		switch (n) {
			case 0:  return x;
			case 1:  return y;
			case 2:  return z;
			default: return 0;
		}
	}
	
	public Vector3D add(IVector vector) 	  	{ return new Vector3D(x + vector.getX().doubleValue(), y + vector.getY().doubleValue(), z + vector.getZ().doubleValue()); }
	public Vector3D subtract(IVector vector)  	{ return new Vector3D(x - vector.getX().doubleValue(), y - vector.getY().doubleValue(), z - vector.getZ().doubleValue()); }
	public Vector3D multiply(IVector vector)  	{ return new Vector3D(x * vector.getX().doubleValue(), y * vector.getY().doubleValue(), z * vector.getZ().doubleValue()); }
	public Vector3D divide(IVector vector) 	  	{ return new Vector3D(x / vector.getX().doubleValue(), y / vector.getY().doubleValue(), z / vector.getZ().doubleValue()); }
	public Vector3D multiply(Number multiplier) { return new Vector3D(x * multiplier.doubleValue(), y * multiplier.doubleValue(), z * multiplier.doubleValue()); }
	
	public Vector3D set(int n, Number num) {
		switch (n) {
			case 0:  return new Vector3D(num, y, z);
			case 1:  return new Vector3D(x, num, z);
			case 2:  return new Vector3D(x, y, num);
			default: return copy();
		}
	}
	
	public Vector3D copy() { return new Vector3D(x, y, z); }
	
	public static Vector3D cast(IVector vector) 						{ return new Vector3D(vector.getX(), vector.getY(), vector.getZ()); }
	public static Vector3D from2DComponents(Number x, Number y) 		{ return new Vector3D(x, y, 0); }
	public static Vector3D fromComponents(Number x, Number y, Number z) { return new Vector3D(x, y, z); }
	public static Vector3D from2DPolar(Number magnitude, Number alpha)  {
		return new Vector3D(
			magnitude.doubleValue() * Math.cos(alpha.doubleValue()),
			magnitude.doubleValue() * Math.sin(alpha.doubleValue()),
			0
		);
	}
	public static Vector3D from3DPolar(Number magnitude, Number alpha, Number beta) {
		return new Vector3D(
			magnitude.doubleValue() * Math.cos(alpha.doubleValue()) * Math.cos(beta.doubleValue()),
			magnitude.doubleValue() * Math.sin(alpha.doubleValue()) * Math.cos(beta.doubleValue()),
			magnitude.doubleValue() * Math.sin(beta.doubleValue())
		);
	}
	
}
