package com.flamingmarshmallow.demo.gui;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.flamingmarshmallow.demo.gui.ObjectList.PageNav;

class ObjectListTest {

	@Test
	void testPageNav_get() {

		Assertions.assertEquals(Collections.emptySet(), PageNav.get(1, 1));
		Assertions.assertEquals(Set.of(PageNav.HAS_NEXT), PageNav.get(1, 2));
		Assertions.assertEquals(Set.of(PageNav.HAS_NEXT), PageNav.get(1, 10));
		Assertions.assertEquals(Set.of(PageNav.HAS_PREV, PageNav.HAS_NEXT), PageNav.get(2, 10));
		Assertions.assertEquals(Set.of(PageNav.HAS_PREV), PageNav.get(10, 10));
		Assertions.assertEquals(Set.of(PageNav.HAS_PREV, PageNav.HAS_NEXT), PageNav.get(3, 10));

		Assertions.assertThrows(IllegalArgumentException.class, () -> PageNav.get(0, 1));
		Assertions.assertThrows(IllegalArgumentException.class, () -> PageNav.get(2, 1));
		
		
	}

}
