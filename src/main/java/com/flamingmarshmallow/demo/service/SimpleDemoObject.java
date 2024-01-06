package com.flamingmarshmallow.demo.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A simple demo object.
 */
public class SimpleDemoObject {
	
	public final String name;
	public final String longDescription;
	public final List<String> tags;
	
	
	public SimpleDemoObject(final String name, final String longDescription, final List<String> tags) {
		this.name = name;
		this.longDescription = longDescription;
		this.tags = Collections.unmodifiableList(tags);
	}

	@Override
	public int hashCode() {
		return Objects.hash(longDescription, name, tags);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleDemoObject other = (SimpleDemoObject) obj;
		return Objects.equals(longDescription, other.longDescription) && Objects.equals(name, other.name)
				&& Objects.equals(tags, other.tags);
	}

	@Override
	public String toString() {
		return String.format("DemoObject [name=%s, longDescription=%s, tags=%s]", name, longDescription, tags);
	}

}
