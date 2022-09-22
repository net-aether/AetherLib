package net.aether.lib.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.*;

public class ClassIndex {
	private final Multimap<Class<? extends Annotation>, Pair<Annotation, Class<?>>>                     classIndex = HashMultimap.create();
	private final Multimap<Class<? extends Annotation>, Triple<Annotation, Constructor<?>, Parameter[]>> ctorIndex = HashMultimap.create();
	private final Multimap<Class<? extends Annotation>, Triple<Annotation, Method, Parameter[]>>       methodIndex = HashMultimap.create();
	private final Multimap<Class<? extends Annotation>, Pair<Annotation, Field>>                        fieldIndex = HashMultimap.create();
	private final Cache<Class<? extends Annotation>, Set<Pair<Annotation, Class<?>>>>                     classCache = CacheBuilder.newBuilder().maximumSize(16).build();
	private final Cache<Class<? extends Annotation>, Set<Triple<Annotation, Constructor<?>, Parameter[]>>> ctorCache = CacheBuilder.newBuilder().maximumSize(16).build();
	private final Cache<Class<? extends Annotation>, Set<Triple<Annotation, Method, Parameter[]>>>       methodCache = CacheBuilder.newBuilder().maximumSize(16).build();
	private final Cache<Class<? extends Annotation>, Set<Pair<Annotation, Field>>>                        fieldCache = CacheBuilder.newBuilder().maximumSize(16).build();
	
	public ClassIndex() { }
	
	public Class<?> put(Class<?> clazz) {
		putClass(clazz);
		putCtors(clazz);
		putMethods(clazz);
		putFields(clazz);
		
		for (Class<?> inner : clazz.getDeclaredClasses())
			put(inner);
		return clazz;
	}
	
	public Class<?> putClass(Class<?> clazz) {
		for (Annotation annotation : clazz.getAnnotations())
			this.classIndex.put(annotation.annotationType(), Pair.of(annotation, clazz));
		this.classCache.invalidateAll();
		return clazz;
	}
	
	public Class<?> putCtors(Class<?> clazz) {
		for (Constructor<?> ctor : clazz.getDeclaredConstructors())
			for (Annotation annotation : ctor.getAnnotations())
				this.ctorIndex.put(annotation.annotationType(), Triple.of(annotation, ctor, ctor.getParameters()));
		this.ctorCache.invalidateAll();
		return clazz;
	}
	
	public Class<?> putMethods(Class<?> clazz) {
		for (Method method : clazz.getDeclaredMethods())
			for (Annotation annotation : method.getAnnotations())
				this.methodIndex.put(annotation.annotationType(), Triple.of(annotation, method, method.getParameters()));
		this.methodCache.invalidateAll();
		return clazz;
	}
	
	public Class<?> putFields(Class<?> clazz) {
		for (Field field : clazz.getDeclaredFields())
			for (Annotation annotation : field.getAnnotations())
				this.fieldIndex.put(annotation.annotationType(), Pair.of(annotation, field));
		this.fieldCache.invalidateAll();
		return clazz;
	}
	
	
	public Set<Pair<Annotation, Class<?>>> getClassesBy(Class<? extends Annotation> annotation) {
		Objects.requireNonNull(annotation, "annotation is null");
		if (!this.classIndex.containsKey(annotation)) return ImmutableSet.of();
		try {
			return this.classCache.get(annotation, () -> ImmutableSet.copyOf(this.classIndex.get(annotation)));
		} catch (ExecutionException e) { } // won't happen
		return ImmutableSet.copyOf(this.classIndex.get(annotation)); // use this as a fallback just to be safe
	}
	
	public Set<Triple<Annotation, Constructor<?>, Parameter[]>> getConstructorsBy(Class<? extends Annotation> annotation) {
		Objects.requireNonNull(annotation, "annotation is null");
		if (!this.ctorIndex.containsKey(annotation)) return ImmutableSet.of();
		try {
			return this.ctorCache.get(annotation, () -> ImmutableSet.copyOf(this.ctorIndex.get(annotation)));
		} catch (ExecutionException e) { } // won't happen
		return ImmutableSet.copyOf(this.ctorIndex.get(annotation)); // use this as a fallback just to be safe
	}
	
	public Set<Triple<Annotation, Method, Parameter[]>> getMethodsBy(Class<? extends Annotation> annotation) {
		Objects.requireNonNull(annotation, "annotation is null");
		if (!this.methodIndex.containsKey(annotation)) return ImmutableSet.of();
		try {
			return this.methodCache.get(annotation, () -> ImmutableSet.copyOf(this.methodIndex.get(annotation)));
		} catch (ExecutionException e) { } // won't happen
		return ImmutableSet.copyOf(this.methodIndex.get(annotation)); // use this as a fallback just to be safe
	}
	
	public Set<Pair<Annotation, Field>> getFieldsBy(Class<? extends Annotation> annotation) {
		Objects.requireNonNull(annotation, "annotation is null");
		if (!this.fieldIndex.containsKey(annotation)) return ImmutableSet.of();
		try {
			return this.fieldCache.get(annotation, () -> ImmutableSet.copyOf(this.fieldIndex.get(annotation)));
		} catch (ExecutionException e) { } // won't happen
		return ImmutableSet.copyOf(this.fieldIndex.get(annotation)); // use this as a fallback just to be safe
	}
}
