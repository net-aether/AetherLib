package net.aether.lib.math;

import net.aether.lib.annotation.WIP;

/*
 * [abstract] still needed?
 */
@WIP
public abstract class Matrix<N extends Number> implements IMatrix<N> {
	protected DataStructure structure;
	protected N[][] data;
	
	protected Matrix(DataStructure structure, N[][] data) {
		this.structure = null;
		this.data = null;
	}
	
	protected Matrix(N[][] data) {
		this(DataStructure.ROWS_COLUMNS, data);
	}

	@Override
	public abstract IMatrix<N> add(IMatrix<N> matrix);
	@Override
	public abstract void       addSelf(IMatrix<N> matrix);
	@Override
	public abstract IMatrix<N> sclMultiply(N scalar);
	@Override
	public abstract void       sclMultiplySelf(N scalar);
	@Override
	public abstract IMatrix<N> multiply(IMatrix<N> matrix);
	@Override
	public abstract void       multiplySelf(IMatrix<N> matrix);
	@Override
	public abstract IMatrix<N> transpose(IMatrix<N> matrix);
	@Override
	public abstract void       transposeSelf(IMatrix<N> matrix);
	@Override
	public N determinant() {
		
		return null;
	}
	
	@Override
	public boolean isSquare() {
		return data.length > 0 && data.length == data[0].length;
	}
	
	@Override
	public abstract <T extends Number> IMatrix<T> convert(Class<T> conversionType);
}
