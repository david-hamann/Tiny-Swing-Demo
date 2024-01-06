package com.flamingmarshmallow.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DemoService implements InOutService<String, SimpleDemoObject> {
	
	private static final Logger LOGGER = LogManager.getLogger(DemoService.class);
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	private final Map<String, SimpleDemoObject> data = Collections.synchronizedMap(new HashMap<String, SimpleDemoObject>());

	
	/**
	 * Loads data from file on the package path.
	 * @param filename
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	public void load(final String filename) throws IOException, URISyntaxException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(filename)))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				
				DataFileLine data = OBJECT_MAPPER.readValue(line, DataFileLine.class);
				
				this.data.put(data.key, data.value);
			}
		}
		LOGGER.debug("data: {}", data);
	}
	
	static class DataFileLine {
		private String key;
		private SimpleDemoObject value;
		
		@JsonCreator
		public DataFileLine(@JsonProperty("key") final String key, @JsonProperty("value") final SimpleDemoObject value) {
			this.key = key;
			this.value = value;
		}
		
	}
	
	
	@Override
	public SimpleDemoObject get(String key) {
		return this.data.get(key);
	}

	@Override
	public List<SimpleDemoObject> getAll(Set<String> keys) {
		return this.data.entrySet().stream()
				   .filter(a -> keys.contains(a.getKey()))
				   .map(Map.Entry::getValue)
				   .collect(Collectors.toList());
	}

	@Override
	public void save(String key, SimpleDemoObject obj) {
		this.data.put(key, obj);
	}

	@Override
	public void delete(String key) {
		this.data.remove(key);
	}
	
}
