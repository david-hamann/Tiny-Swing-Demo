package com.flamingmarshmallow.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.IllegalArgumentException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.LinkedHashMap;
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
	
	private final Map<String, SimpleDemoObject> data = Collections.synchronizedMap(new LinkedHashMap<String, SimpleDemoObject>());
	
	/**
	 * InOutService<String, SimpleDemoObject> service = DemoService.Builder().withDemoData().build();
	 */
	public static class Builder {
		
		private String dataFile = "";
		private boolean exitOnError = false;

		private Builder() {
			//empty
		}
		
		public Builder withDemoData(final String dataFile) {
			return this.withDemoData(dataFile, false);
		}

		public Builder withDemoData(final String dataFile, final boolean exitOnError) {
			this.dataFile = dataFile;
			return this;
		}
		
		public InOutService<String, SimpleDemoObject> build() {
			DemoService service = new DemoService();
			try {
				service.load(this.dataFile);
			} catch (IOException | URISyntaxException ex) {
				LOGGER.error("can't load data file: {}", ex);
				if (exitOnError) {
					LOGGER.error("Exiting due to error");
					System.exit(1);
				}
			}
			return service;
		}
		
	}
	
	public static Builder getBuilder() {
		return new Builder();
	}
	
	
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
	public Map<String, SimpleDemoObject> getAll(final Set<String> keys) {
		return this.data.entrySet().stream()
				   .filter(e -> keys.contains(e.getKey()))
	 	           .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new));
	}
	
	/**
	 * Uses a LinkedHashMap implementation.
	 */
	@Override
	public LinkedHashMap<String, SimpleDemoObject> getAll(final int offset, final int limit) {
		if (offset < 0 || offset >= this.data.size() || limit < 0) {
			throw new IllegalArgumentException();
		}
		if (this.data.size() == 0) {
			return new LinkedHashMap<>();
		}
		
		return this.data.entrySet().stream()
	 	           .skip(offset)
	 	           .limit(limit)
	 	           .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new));
	}

	@Override
	public void save(String key, SimpleDemoObject obj) {
		this.data.put(key, obj);
	}

	@Override
	public void delete(String key) {
		this.data.remove(key);
	}
	
	Map<String, SimpleDemoObject> dump() {
		return Collections.unmodifiableMap(this.data);
	}
	
}
