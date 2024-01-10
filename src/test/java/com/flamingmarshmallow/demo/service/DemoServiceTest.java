package com.flamingmarshmallow.demo.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.flamingmarshmallow.demo.service.DemoService.OffsetOutOfRange;
import com.flamingmarshmallow.demo.service.InOutService.Data;

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
	
	
//	@TestFactory
//	Stream<DynamicTest> getPage_page_size() {
//		List<TestData>
//	}
	

	class GetAllTest {
		String name;
		int size;
		int offset;
		int limit;
		int expected;
		boolean error;
		
		GetAllTest(String name, int size, int offset, int limit, int expected, boolean error) {
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

	@TestFactory
	Stream<DynamicTest> testGetAll_offset_limit() {
		List<GetAllTest> tests = List.of(
				new GetAllTest("1", 100, 0, 1, 1, false),
				new GetAllTest("10", 100, 0, 10, 10, false),
				new GetAllTest("100", 100, 0, 100, 100, false),
				new GetAllTest("over", 100, 0, 200, 100, false),
				new GetAllTest("almost", 100, 99, 1, 1, false),
				new GetAllTest("even", 100, 99, 2, 1, false),
				new GetAllTest("over one", 100, 99, 3, 1, false),
				new GetAllTest("none", 100, 100, 3, 0, true),
				new GetAllTest("bad offset", 100, -1, 3, 0, true),
				new GetAllTest("badder offset", 100, 123, 5, 0, true)
		);

		return tests.stream().map(test -> DynamicTest.dynamicTest(test.name, () -> {
			DemoService service = new DemoService();
			for (int i = 0; i < test.size; i++) {
				service.save(Long.valueOf(i), new Widget("v" + i, "", Collections.emptyList(), 0, 0));
			}

			if (test.error) {
				Assertions.assertThrows(OffsetOutOfRange.class, () -> {
					service.getAll(test.offset, test.limit);
				}, test.toString());
			} else {
				List<Data<Long, Widget>> m = service.getAll(test.offset, test.limit);
				Assertions.assertEquals(test.expected, m.size(), test.toString());
			}
			
		}));

	}
	
}
