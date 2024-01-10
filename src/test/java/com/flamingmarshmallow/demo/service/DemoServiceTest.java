package com.flamingmarshmallow.demo.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import com.flamingmarshmallow.demo.service.InOutService.Data;
import com.flamingmarshmallow.demo.service.Paging.InvalidPageNumber;
import com.flamingmarshmallow.demo.service.Paging.InvalidPageSize;
import com.flamingmarshmallow.demo.service.Paging.OffsetOutOfRange;

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

	@TestFactory
	Stream<DynamicTest> getPage_page_size() {
		record Test (
			String name,
			String[] widgetNames,
			int pageSize,
			int expectedfirstPageRowCount,
			int expectedlastPageRowCount,
			int expectedPageCount
		) {};
		Test[] tests = {
				new Test("empty", new String[] {}, 1, 0, 0, 1),
				new Test("1", new String[] {"0"}, 3, 1, 1, 1),
				new Test("3", new String[] {"0", "1", "2"}, 3, 3, 3, 1),
				new Test("4", new String[] {"0", "1", "2", "3"}, 3, 3, 1, 2),
				new Test("6", new String[] {"0", "1", "2", "3","4","5"}, 3, 3, 3, 2),
				new Test("6", new String[] {"0", "1", "2", "3","4","5","6","7"}, 3, 3, 2, 3)
		};
		
		return Arrays.asList(tests).stream()
				.map(test -> DynamicTest.dynamicTest(test.name(), () -> {
					
					//Paging<Long, Widget>
					DemoService service = new DemoService();
					for (int i = 0; i < test.widgetNames().length; i++) {
						service.save(Long.valueOf(i), new Widget(test.widgetNames()[i], "", Collections.emptyList(), 0, 0));
					}
					
					Assertions.assertEquals(test.expectedPageCount(), service.pageCount(test.pageSize()));

					//There is always one page
					Assertions.assertNotNull(service.getPage(1, 3));
					Assertions.assertNotNull(service.getPage(1, 500));
					
					//no non-positive page sizes
					Assertions.assertThrows(InvalidPageSize.class, () -> service.getPage(1, 0));
					Assertions.assertThrows(InvalidPageSize.class, () -> service.getPage(1, -1));
					
					//no non-positive page numbers
					Assertions.assertThrows(InvalidPageNumber.class, () -> service.getPage(-1, 5));
					Assertions.assertThrows(InvalidPageNumber.class, () -> service.getPage(0, 5));

					
					for (int i = 1; i <= test.expectedPageCount() + 1; i++) {
						final int page = i;
						if (page == 1) {
							//page 1 always exists
							List<Data<Long, Widget>> rows = service.getPage(page, test.pageSize());
							Assertions.assertNotNull(rows);
							Assertions.assertEquals(test.expectedfirstPageRowCount(), rows.size());
						}
						
						if (page == test.expectedPageCount()) {
							//page n exists
							List<Data<Long, Widget>> rows = service.getPage(page, test.pageSize());
							Assertions.assertNotNull(rows);
							Assertions.assertEquals(test.expectedlastPageRowCount(), rows.size());
						}
						
						if (page == test.expectedPageCount() + 1) {
							//page n+1 fails
							Assertions.assertThrows(InvalidPageNumber.class, () -> service.getPage(page, test.pageSize()));
						}
						
					}
					
				}));
		
	}
	

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
