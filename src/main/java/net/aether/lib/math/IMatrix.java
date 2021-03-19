package net.aether.lib.math;

public interface IMatrix<N extends Number> {
	IMatrix<N> add(IMatrix<N> matrix);
	void       addSelf(IMatrix<N> matrix);
	IMatrix<N> sclMultiply(N scalar);
	void       sclMultiplySelf(N scalar);
	IMatrix<N> multiply(IMatrix<N> matrix);
	void       multiplySelf(IMatrix<N> matrix);
	IMatrix<N> transpose(IMatrix<N> matrix);
	void       transposeSelf(IMatrix<N> matrix);
	N          determinant();
	boolean    isSquare();
	<T extends Number> IMatrix<T> convert(Class<T> conversionType);
	
	public static enum DataStructure {
		ROWS_COLUMNS,
		COLUMNS_ROWS;
	}
}
