package net.aether.lib.reflect;

public final class IndexingClassLoader extends ClassLoader {
	private final ClassIndex index;
	
	public IndexingClassLoader() {
		this(new ClassIndex());
	}
	
	public IndexingClassLoader(ClassIndex index) {
		super();
		this.index = index;
	}
	
	public IndexingClassLoader(ClassLoader parent) {
		this(parent, new ClassIndex());
	}
	
	public IndexingClassLoader(ClassLoader parent, ClassIndex index) {
		super(parent);
		this.index = index;
	}
	
	public IndexingClassLoader(String name, ClassLoader parent) {
		this(name, parent, new ClassIndex());
	}
	
	public IndexingClassLoader(String name, ClassLoader parent, ClassIndex index) {
		super(name, parent);
		this.index = index;
	}
	
	
	@Override
	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		return this.index.put(super.loadClass(name, resolve));
	}
	
	public ClassIndex getIndex() {
		return this.index;
	}
}
