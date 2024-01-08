package com.flamingmarshmallow.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DemoService implements InOutService<Long, SimpleDemoObject> {
	
	private static final Logger LOGGER = LogManager.getLogger(DemoService.class);
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	private final Map<Long, SimpleDemoObject> data = Collections.synchronizedMap(new LinkedHashMap<Long, SimpleDemoObject>());
	
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
		
		public InOutService<Long, SimpleDemoObject> build() {
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
				LOGGER.info("loaded data into db: key={}", data.key);
			}
		}
		LOGGER.debug("data: {}", data);
	}
	
	static class DataFileLine {
		private Long key;
		private SimpleDemoObject value;
		
		@JsonCreator
		public DataFileLine(@JsonProperty("key") final Long key, @JsonProperty("value") final SimpleDemoObject value) {
			this.key = key;
			this.value = value;
		}
		
	}
	
	
	@Override
	public SimpleDemoObject get(Long key) {
		return this.data.get(key);
	}
	
	@Override
	public Map<Long, SimpleDemoObject> getAll(final Set<Long> keys) {
		return this.data.entrySet().stream()
				   .filter(e -> keys.contains(e.getKey()))
	 	           .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new));
	}

	/**
	 * Returns if the name includes the search term.
	 */
	@Override
	public List<Map.Entry<Long, SimpleDemoObject>> search(final String searchTerm, final int offset, final int limit) {
		if (offset < 0 || offset >= this.data.size() || limit < 0) {
			throw new IllegalArgumentException();
		}
		if (this.data.size() == 0) {
			return Collections.emptyList();
		}
		
		return this.data.entrySet().stream()
				.filter(e -> e.getValue().name.indexOf(searchTerm) >= 0)
				.skip(offset)
				.limit(limit)
				.collect(Collectors.toList());
	}
	
	/**
	 * Uses a LinkedHashMap implementation.
	 */
	@Override
	public List<Map.Entry<Long, SimpleDemoObject>> getAll(final int offset, final int limit) {
		if (offset < 0 || offset >= this.data.size() || limit < 0) {
			throw new IllegalArgumentException();
		}
		if (this.data.size() == 0) {
			return Collections.emptyList();
		}
		
		return this.data.entrySet().stream()
	 	           .skip(offset)
	 	           .limit(limit)
	 	           .collect(Collectors.toList());
	}
	
	@Override
	public Long save(SimpleDemoObject obj) {
		long newId;
		do {
			newId = ThreadLocalRandom.current().nextLong(10000, 100000);
		} while (this.data.keySet().contains(newId));
		this.save(newId, obj);
		return newId;
	}

	@Override
	public void save(Long key, SimpleDemoObject obj) {
		this.data.put(key, obj);
	}

	@Override
	public void delete(Long key) {
		this.data.remove(key);
	}
	
	Map<Long, SimpleDemoObject> dump() {
		return Collections.unmodifiableMap(this.data);
	}
	
}
