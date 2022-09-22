package net.aether.lib.data;

import com.google.common.base.Preconditions;

import java.util.Objects;
import java.util.regex.Pattern;

public final class NamespacedIdentifier implements Comparable<NamespacedIdentifier> {
	private static final Pattern NAMESPACE_PATTERN = Pattern.compile("[a-zA-Z0-9._-]+");
	private static final Pattern ID_PATTERN = Pattern.compile("[a-zA-Z0-9\\+._-]+");
	
	public static final String DEFAULT_NAMESPACE = "aelib";
	public static final char DEFAULT_SEPARATOR = ':';
	
	private final String namespace;
	private final String id;
	
	public NamespacedIdentifier(String namespace, String id) {
		Objects.requireNonNull(namespace);
		Objects.requireNonNull(id);
		Preconditions.checkArgument(NAMESPACE_PATTERN.matcher(namespace).matches(), "namespace must match " + NAMESPACE_PATTERN.toString());
		Preconditions.checkArgument(ID_PATTERN.matcher(namespace).matches(), "id must match " + ID_PATTERN.toString());
		
		this.namespace = namespace.intern();
		this.id = id.intern();
	}
	
	public NamespacedIdentifier(String[] id) {
		this(id[0], id[1]);
	}
	
	public NamespacedIdentifier(String id) {
		this(decompose(id, DEFAULT_SEPARATOR));
	}
	
	public String getNamespace() {
		return this.namespace;
	}
	
	public String getId() {
		return this.id;
	}
	
	@Override
	public int hashCode() {
		return this.namespace.hashCode() ^ this.id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		return obj instanceof NamespacedIdentifier ni && this.namespace == ni.namespace && this.id == ni.id;
	}
	
	@Override
	public String toString() {
		return this.namespace + ":" + this.id;
	}
	
	@Override
	public int compareTo(NamespacedIdentifier o) {
		int i = this.namespace.compareTo(o.namespace);
		return i == 0 ? this.id.compareTo(o.id) : i;
	}
	
	
	public static String[] decompose(String id, char sep) {
		String[] parts = new String[] { DEFAULT_NAMESPACE, id };
		
		int idx = id.indexOf(sep);
		if (idx >= 0) {
			parts[1] = id.substring(idx + 1);
			if (idx >= 1)
				parts[0] = id.substring(0, idx);
		}
		
		return parts;
	}
}
