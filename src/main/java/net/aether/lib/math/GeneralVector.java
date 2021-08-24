package net.aether.lib.math;

import net.aether.lib.data.Pair;

/**
 * Represents a Vector with up to {@link Integer#MAX_VALUE} (because they are stored in an array, that's the limit) components
 * @author Kilix
 *
 */
public class GeneralVector implements IVector {
	
	protected GeneralVector(Number... components) {
		double[] tmp = new double[components.length];
		for (int i = 0; i < components.length; i++) tmp[i] = components[i].doubleValue();
		this.components = tmp;
	}
	
	protected final double[] components;
	
	public Number getMagnitude() {
		double a = 0;
		for (Number n : components) a += Math.pow(n.doubleValue(), 2);
		return Math.sqrt(a);
	}
	/**
	 * If the Vector (for some reason) has less than 2 components this will simply return 0
	 */
	public Number get2DAngle() {
		if (components.length < 2) return 0;
		return Math.atan2(components[1], components[0]);
	}
	/**
	 * If the Vector (for some reason) has less than 3 components this will simply return a pair with values 0, 0
	 */
	public Pair<Number, Number> get3DAngles() {
		if (components.length < 3) return Pair.of(0, 0);
		return Pair.of(Math.atan2(components[1], components[0]), Math.atan2(components[2], components[0]));
	}
	public Number[] get4DAngles() {
		return new Number[] {
				Math.atan2(components[1], components[0]),
				Math.atan2(components[2], components[0]),
				Math.atan2(components[3], components[0]),
		};
	}
	public Number[] getNAngles(int n) {
		if (n < 0 || components.length < 2) return new Number[0];
		
		Number[] out = new Number[Math.min(n, components.length - 1)];
		for (int i = 0; i < out.length; i++) out[i] = Math.atan2(components[i + 1], components[0]);
		return out;
	}
	
	public int getComponentCount() { return components.length; }
	public Number get(int n) { return n < 0 || n >= components.length ? 0 : components[n]; }
	
	public GeneralVector add(IVector vector) {
		Double[] tmp = new Double[components.length];
		for (int i = 0; i < components.length; i++) tmp[i] = components[i] + vector.get(i).doubleValue();
		return new GeneralVector(tmp);
	}
	public GeneralVector subtract(IVector vector) {
		Double[] tmp = new Double[components.length];
		for (int i = 0; i < components.length; i++) tmp[i] = components[i] - vector.get(i).doubleValue();
		return new GeneralVector(tmp);
	}
	public GeneralVector multiply(IVector vector) {
		Double[] tmp = new Double[components.length];
		for (int i = 0; i < components.length; i++) tmp[i] = components[i] * vector.get(i).doubleValue();
		return new GeneralVector(tmp);
	}
	public GeneralVector divide(IVector vector) {
		Double[] tmp = new Double[components.length];
		for (int i = 0; i < components.length; i++) tmp[i] = components[i] / vector.get(i).doubleValue();
		return new GeneralVector(tmp);
	}
	public GeneralVector multiply(Number multiplier) {
		Double[] tmp = new Double[components.length];
		for (int i = 0; i < components.length; i++) tmp[i] = components[i] * multiplier.doubleValue();
		return new GeneralVector(tmp);
	}
	
	public GeneralVector set(int n, Number num) {
		if (n >= components.length) return copy();
		Double[] tmp = new Double[components.length];
		for (int i = 0; i < components.length; i++) tmp[i] = components[i];
		tmp[n] = num.doubleValue();
		return new GeneralVector(tmp);
	}
	
	public GeneralVector copy() {
		Double[] tmp = new Double[components.length];
		for (int i = 0; i < components.length; i++) tmp[i] = components[i];
		return new GeneralVector(tmp);
	}
	
	public static GeneralVector cast(IVector vector) {
		Double[] tmp = new Double[vector.getComponentCount()];
		for (int i = 0; i < vector.getComponentCount(); i++) tmp[i] = vector.get(i).doubleValue();
		return new GeneralVector(tmp);
	}
	public static GeneralVector fomComponents(Number... components) { return new GeneralVector(components); }
}
