package net.aether.lib.reflect;

import static org.objectweb.asm.ClassReader.*;
import static org.objectweb.asm.Opcodes.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipFile;

import org.objectweb.asm.*;

public class ClassScanner { // TODO scan method signatures for annotations, too ._. ;w;
	private final ZipFile file;
	
	public ClassScanner(Path pathToScan) throws IOException {
		if (! pathToScan.toString().endsWith(".jar")) throw new IllegalArgumentException("scan path must point to a .jar file");
		this.file = new ZipFile(pathToScan.toFile());
	}
	
	public Set<Class<?>> loadClassesBy(Class<?> clazz) throws ClassNotFoundException {
		return loadClassesBy(clazz, Thread.currentThread().getContextClassLoader());
	}
	
	public Set<Class<?>> loadClassesBy(Class<?> clazz, ClassLoader loader) throws ClassNotFoundException {
		return loadClasses(listClassesBy(clazz), loader);
	}
	
	public Set<Class<?>> indexClassesBy(Class<?> clazz, ClassIndex ci) throws ClassNotFoundException {
		return indexClassesBy(clazz, Thread.currentThread().getContextClassLoader(),ci);
	}
	
	public Set<Class<?>> indexClassesBy(Class<?> clazz, ClassLoader loader, ClassIndex ci) throws ClassNotFoundException {
		Set<Class<?>> classes = loadClassesBy(clazz, loader);
		classes.forEach(ci::put);
		return classes;
	}
	
	public Set<Class<?>> loadClassesByMethodAnnotation(Class<? extends Annotation> clazz) throws ClassNotFoundException {
		return loadClassesByMethodAnnotation(clazz, Thread.currentThread().getContextClassLoader());
	}
	
	public Set<Class<?>> loadClassesByMethodAnnotation(Class<? extends Annotation> clazz, ClassLoader loader) throws ClassNotFoundException {
		return loadClasses(listClassesByMethodAnnotation(clazz), loader);
	}
	
	public Set<Class<?>> indexClassesByMethodAnnotation(Class<? extends Annotation> clazz, ClassIndex ci) throws ClassNotFoundException {
		return indexClassesByMethodAnnotation(clazz, Thread.currentThread().getContextClassLoader(),ci);
	}
	
	public Set<Class<?>> indexClassesByMethodAnnotation(Class<? extends Annotation> clazz, ClassLoader loader, ClassIndex ci) throws ClassNotFoundException {
		Set<Class<?>> classes = loadClassesByMethodAnnotation(clazz, loader);
		classes.forEach(ci::put);
		return classes;
	}
	
	private Set<Class<?>> loadClasses(Set<String> classnames, ClassLoader loader) throws ClassNotFoundException {
		Set<Class<?>> classes = new HashSet<>();
		for (String classname : classnames)
			classes.add(Class.forName(classname, true, loader));
		return classes;
	}
	
	
	@SuppressWarnings("unchecked")
	public synchronized Set<String> listClassesBy(Class<?> clazz) {
		if (clazz.isAnnotation())
			return listClassesByAnnotation((Class<? extends Annotation>) clazz);
		return listClassesBySuperOrInterface(clazz);
	}
	
	/**
	 * Only scans for direct superclasses/-interfaces
	 */
	public synchronized Set<String> listClassesBySuperOrInterface(Class<?> superOrInterfaceClass) {
		Set<String> classnames = new HashSet<>();
		String superOrInterfaceName = Type.getInternalName(superOrInterfaceClass);
		boolean isInterface = superOrInterfaceClass.isInterface();
		
		ClassVisitor cv = new ClassVisitor(ASM9) {
			@Override
			public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
				name = name.replaceAll("/", ".");
				if (isInterface)
					for (String interf : interfaces)
						if (interf != null && interf.equals(superOrInterfaceName)) {
							classnames.add(name);
							break;
						}
						else if (superName != null && superName.equals(superOrInterfaceName))
							classnames.add(name);
			}
		};
		
		this.file.stream().forEach(entry -> {
			if (entry.getName().endsWith(".class"))
				try (InputStream is = this.file.getInputStream(entry)) {
					ClassReader cr = new ClassReader(is);
					cr.accept(cv, SKIP_CODE);
				} catch (Exception e) {
					System.err.println("Error reading class " + entry.getName());
					e.printStackTrace(); // TODO logger
				}
		});
		
		return classnames;
	}
	
	public synchronized Set<String> listClassesByAnnotation(Class<? extends Annotation> annotationClass) {
		Set<String> classnames = new HashSet<>();
		String annotationDescriptor = Type.getDescriptor(annotationClass);
		
		ClassVisitor cv = new ClassVisitor(ASM9) {
			String name = null;
			
			@Override
			public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
				this.name = name.replaceAll("/", ".");
			}
			
			@Override
			public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
				if (descriptor != null && descriptor.equals(annotationDescriptor))
					classnames.add(this.name);
				return null;
			}
		};
		
		this.file.stream().forEach(entry -> {
			if (entry.getName().endsWith(".class"))
				try (InputStream is = this.file.getInputStream(entry)) {
					ClassReader cr = new ClassReader(is);
					cr.accept(cv, SKIP_CODE);
				} catch (Exception e) {
					System.err.println("Error reading class " + entry.getName());
					e.printStackTrace(); // TODO logger
				}
		});
		
		return classnames;
	}
	
	public synchronized Set<String> listClassesByMethodAnnotation(Class<? extends Annotation> annotationClass) {
		Set<String> classnames = new HashSet<>();
		String annotationDescriptor = Type.getDescriptor(annotationClass);
		
		ClassVisitor cv = new ClassVisitor(ASM9) {
			String cname = null;
			
			@Override
			public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
				this.cname = name.replaceAll("/", ".");
			}
			
			@Override
			public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
				return new MethodVisitor(ASM9) {
					@Override
					public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
						if (descriptor != null && descriptor.equals(annotationDescriptor))
							classnames.add(cname); // cannot refer to this field using 'this' because anonymous outer classes cannot be referenced
						return null;
					}
				};
			}
		};
		
		this.file.stream().forEach(entry -> {
			if (entry.getName().endsWith(".class"))
				try (InputStream is = this.file.getInputStream(entry)) {
					ClassReader cr = new ClassReader(is);
					cr.accept(cv, SKIP_CODE);
				} catch (Exception e) {
					System.err.println("Error reading class " + entry.getName());
					e.printStackTrace(); // TODO logger
				}
		});
		
		return classnames;
	}
}
