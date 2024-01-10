package com.flamingmarshmallow.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DemoService implements InOutService<Long, Widget>, Paging<Long, Widget>{
	
	private static final Logger LOGGER = LogManager.getLogger(DemoService.class);
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	private final Map<Long, Widget> data = Collections.synchronizedMap(new LinkedHashMap<>());

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
		
		public InOutService<Long, Widget> build() {
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
		private Widget value;
		
		@JsonCreator
		public DataFileLine(@JsonProperty("key") final Long key, @JsonProperty("value") final Widget value) {
			this.key = key;
			this.value = value;
		}
		
	}
	
	
	@Override
	public Widget get(Long key) {
		return this.data.get(key);
	}
	
	@Override
	public List<Data<Long, Widget>> getAll(final Set<Long> keys) {
		return this.data.entrySet().stream()
				   .filter(e -> keys.contains(e.getKey()))
				   .map(e -> new Data<>(e.getKey(), e.getValue()))
				   .collect(Collectors.toList());
	}

	/**
	 * Returns if the name includes the search term.
	 */
	@Override
	public List<Data<Long, Widget>> search(final String searchTerm, final int offset, final int limit) {
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
				.map(e -> new Data<>(e.getKey(), e.getValue()))
				.collect(Collectors.toList());
	}
	
	/**
	 * Uses a LinkedHashMap implementation.
	 */
	@Override
	public List<Data<Long, Widget>> getAll(final int offset, final int limit) {
		if (offset < 0 || offset >= this.data.size() || limit < 0) {
			throw new OffsetOutOfRange();
		}
		if (this.data.size() == 0) {
			return Collections.emptyList();
		}
		
		return this.data.entrySet().stream()
	 	           .skip(offset)
	 	           .limit(limit)
				   .map(e -> new Data<>(e.getKey(), e.getValue()))
	 	           .collect(Collectors.toList());
	}
	

	private int defaultPageSize = 25;
	public void setDefaultPageSize(final int pageSize) {
		this.defaultPageSize = pageSize;
	}
	public List<Data<Long, Widget>> getPage(final int pageNumber) {
		return this.getPage(pageNumber, this.defaultPageSize);
	}

	@SuppressWarnings("serial")
	public static class OffsetOutOfRange extends RuntimeException {}
	@SuppressWarnings("serial")
	public static class InvalidPageNumber extends RuntimeException {}
	@SuppressWarnings("serial")
	public static class InvalidPageSize extends RuntimeException {}
	
	
	/**
	 * There's always at least one page, although it may be empty.
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<Data<Long, Widget>> getPage(final int pageNumber, final int pageSize) {
		//1 -> 0, pageSize;
		if (pageNumber <= 0) {
			throw new InvalidPageNumber();
		}
		if (pageSize <= 0) {
			throw new InvalidPageSize();
		}

		int offset = (pageNumber -1) * pageSize;
		
		List<Data<Long, Widget>> rows = this.getAll(offset, pageSize)
					.stream()
					.collect(Collectors.toList());
		
		if (rows.size() == 0) {
			if (pageNumber == 1) {
				return Collections.emptyList(); //there's always page 1
			}
			throw new InvalidPageNumber();
		}
		
		return rows;
	}
	
	public int pageCount(final int pageSize) {
		// this should never get caught, because the getPage should catch it
		// if (pageSize <= 0) { throw new InvalidPageSize(); }
		int rows = this.data.size();
		int pages = rows/pageSize;
		if ( rows % pageSize > 0) {
			pages++;
		}
		if (pages == 0) {
			return 1; //there's always one page
		}
		return pages;
	}
	
	@Override
	public Long save(Widget obj) {
		long newId;
		do {
			newId = ThreadLocalRandom.current().nextLong(10000, 100000);
		} while (this.data.keySet().contains(newId));
		this.save(newId, obj);
		return newId;
	}

	@Override
	public void save(Long key, Widget obj) {
		this.data.put(key, obj);
	}

	@Override
	public Widget delete(Long key) {
		return this.data.remove(key);
	}
	
	Map<Long, Widget> dump() {
		return Collections.unmodifiableMap(this.data);
	}


	@Override
	public int size() {
		return this.data.size();
	}
	
}
