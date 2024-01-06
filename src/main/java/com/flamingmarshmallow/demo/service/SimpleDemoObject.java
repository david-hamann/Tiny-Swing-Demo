package com.flamingmarshmallow.demo.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A simple demo object.
 */
public class SimpleDemoObject {
	
	public final String name;
	public final String longDescription;
	public final List<String> tags;
	public final long created;
	
	
	@JsonCreator
	public SimpleDemoObject(@JsonProperty("name") final String name,
							@JsonProperty("longDescription") final String longDescription,
							@JsonProperty("tags") final List<String> tags,
							@JsonProperty("created") final long created) {
		this.name = name;
		this.longDescription = longDescription;
		this.tags = Collections.unmodifiableList(tags);
		this.created = created;
	}

	@Override
	public String toString() {
		return String.format("DemoObject [name=%s, longDescription=%s, tags=%s, created=%s]", name, longDescription, tags, created);
	}

	@Override
	public int hashCode() {
		return Objects.hash(created, longDescription, name, tags);
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
		return created == other.created && Objects.equals(longDescription, other.longDescription)
				&& Objects.equals(name, other.name) && Objects.equals(tags, other.tags);
	}

}
