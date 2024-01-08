package com.flamingmarshmallow.demo.service;

import java.lang.Exception;
import java.lang.String;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

class DemoServiceTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	class TestData {
		String name;
		int size;
		int offset;
		int limit;
		int expected;
		boolean error;
		
		TestData(String name, int size, int offset, int limit, int expected, boolean error) {
			this.name = name;
			this.size = size;
			this.offset = offset;
			this.limit = limit;
			this.expected = expected;
			this.error = error;
		}
		
		@Override
		public String toString() {
			return String.format("{name=%s, size=%s, offset=%s, limit=%s, expected=%s, error=%s}", name, size, offset, limit, expected, error);
		}
	}
	
	//TODO testGetAll_set
	
	@TestFactory
	Stream<DynamicTest> testGetAll_offset_limit() {
		List<TestData> tests = List.of(
				new TestData("1", 100, 0, 1, 1, false),
				new TestData("10", 100, 0, 10, 10, false),
				new TestData("100", 100, 0, 100, 100, false),
				new TestData("over", 100, 0, 200, 100, false),
				new TestData("almost", 100, 99, 1, 1, false),
				new TestData("even", 100, 99, 2, 1, false),
				new TestData("over one", 100, 99, 3, 1, false),
				new TestData("none", 100, 100, 3, 0, true),
				new TestData("bad offset", 100, -1, 3, 0, true),
				new TestData("badder offset", 100, 123, 5, 0, true)
		);

		return tests.stream().map(test -> DynamicTest.dynamicTest(test.name, () -> {
			DemoService service = new DemoService();
			for (int i = 0; i < test.size; i++) {
				service.save(Long.valueOf(i), new Widget("v" + i, "", Collections.emptyList(), 0, 0));
			}

			if (test.error) {
				Assertions.assertThrows(IllegalArgumentException.class, () -> {
					service.getAll(test.offset, test.limit);
				}, test.toString());
			} else {
				List<Map.Entry<Long, Widget>> m = service.getAll(test.offset, test.limit);
				Assertions.assertEquals(test.expected, m.size(), test.toString());
			}
			
		}));

	}
	
}
