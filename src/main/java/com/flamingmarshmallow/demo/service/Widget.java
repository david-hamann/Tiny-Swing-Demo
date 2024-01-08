package com.flamingmarshmallow.demo.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A simple demo object.
 */
public class Widget {
	
	public final String name;
	public final String description;
	public final List<String> attributes;
	public final long created;
	public final long updated;

	
	public static final Widget EMPTY = new Widget("", "", Collections.emptyList(), 0, 0);
	
	@JsonCreator
	public Widget(@JsonProperty("name") final String name,
							@JsonProperty("longDescription") final String longDescription,
							@JsonProperty("tags") final List<String> tags,
							@JsonProperty("created") final long created,
							@JsonProperty("updated") final long updated) {
		this.name = name;
		this.description = longDescription;
		this.attributes = Collections.unmodifiableList(tags);
		this.created = created;
		this.updated = updated;
	}

	@Override
	public String toString() {
		return String.format("DemoObject [name=%s, description=%s, attributes=%s, created=%s, updated=%s]", name, description, attributes, created, updated);
	}

	@Override
	public int hashCode() {
		return Objects.hash(attributes, description, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Widget other = (Widget) obj;
		return Objects.equals(attributes, other.attributes) && Objects.equals(description, other.description)
				&& Objects.equals(name, other.name);
	}

}
