package net.aether.lib.math;

import net.aether.lib.data.Pair;

public class Vector4D implements IVector {
	
	protected Vector4D(Number x, Number y, Number z, Number w) {
		this.x = x.doubleValue();
		this.y = y.doubleValue();
		this.z = z.doubleValue();
		this.w = w.doubleValue();
	}
	
	protected final double x, y, z, w;
	
	public Number getMagnitude() {
		return Math.sqrt(
			Math.pow(x, 2) +
			Math.pow(y, 2) +
			Math.pow(z, 2) +
			Math.pow(w, 2)
		);
	}
	public Number get2DAngle() { return Math.atan2(y, x); }
	public Pair<Number, Number> get3DAngles() {
		return Pair.of(
			Math.atan2(y, x),
			Math.atan2(z, x)
		);
	}
	public Number[] get4DAngles() {
		return new Number[] {
				Math.atan2(y, x),
				Math.atan2(z, x),
				Math.atan2(w, x),
		};
	}
	public Number[] getNAngles(int n) {
		if (n < 0) return new Number[0];
		
		Number[] out = new Number[n];
		switch(n) {
			case 4:
				out[3] = w;
			case 3:
				out[2] = z;
			case 2:
				out[1] = y;
			case 1:
				out[0] = x;
		}
		
		return out;
	}

	public int getComponentCount() { return 4; }
	public Number get(int n) {
		switch (n) {
			case 0:  return x;
			case 1:  return y;
			case 2:  return z;
			case 3:  return w;
			default: return 0;
		}
	}

	public Vector4D add(IVector vector) 		{ return new Vector4D(x + vector.getX().doubleValue(), y + vector.getY().doubleValue(), z + vector.getZ().doubleValue(), w + vector.getW().doubleValue()); }
	public Vector4D subtract(IVector vector) 	{ return new Vector4D(x - vector.getX().doubleValue(), y - vector.getY().doubleValue(), z - vector.getZ().doubleValue(), w - vector.getW().doubleValue()); }
	public Vector4D multiply(IVector vector) 	{ return new Vector4D(x * vector.getX().doubleValue(), y * vector.getY().doubleValue(), z * vector.getZ().doubleValue(), w * vector.getW().doubleValue()); }
	public Vector4D divide(IVector vector) 		{ return new Vector4D(x / vector.getX().doubleValue(), y / vector.getY().doubleValue(), z / vector.getZ().doubleValue(), w / vector.getW().doubleValue()); }
	public Vector4D multiply(Number multiplier) {
		return new Vector4D(
			x * multiplier.doubleValue(),
			y * multiplier.doubleValue(),
			z * multiplier.doubleValue(),
			w * multiplier.doubleValue()
		);
	}
	
	public Vector4D set(int n, Number num) {
		switch (n) {
			case 0:  return new Vector4D(num, y, z, w);
			case 1:  return new Vector4D(x, num, z, w);
			case 2:  return new Vector4D(x, y, num, w);
			case 3:  return new Vector4D(x, y, z, num);
			default: return copy();
		}
	}
	
	public Vector4D copy() { return new Vector4D(x, y, z, w); }
	
	public static Vector4D cast(IVector vector) {
		return new Vector4D(
			vector.getX(),
			vector.getY(),
			vector.getZ(),
			vector.getW()
		);
	}
	public static Vector4D from2DComponents(Number x, Number y) { return new Vector4D(x, y, 0, 0); }
	public static Vector4D from3DComponents(Number x, Number y, Number z) { return new Vector4D(x, y, z, 0); }
	public static Vector4D from4DComponents(Number x, Number y, Number z, Number w) { return new Vector4D(x, y, z, w); }
	public static Vector4D from2DPolar(Number magnitude, Number alpha)  {
		return new Vector4D(
			magnitude.doubleValue() * Math.cos(alpha.doubleValue()),
			magnitude.doubleValue() * Math.sin(alpha.doubleValue()),
			0,
			0
		);
	}
	public static Vector4D from3DPolar(Number magnitude, Number alpha, Number beta) {
		return new Vector4D(
			magnitude.doubleValue() * Math.cos(alpha.doubleValue()) * Math.cos(beta.doubleValue()),
			magnitude.doubleValue() * Math.sin(alpha.doubleValue()) * Math.cos(beta.doubleValue()),
			magnitude.doubleValue() * Math.sin(beta.doubleValue()),
			0
		);
	}
	// no from4DPolar, cuz i don't have any idea how to even comprehend 4D vectors...
}
