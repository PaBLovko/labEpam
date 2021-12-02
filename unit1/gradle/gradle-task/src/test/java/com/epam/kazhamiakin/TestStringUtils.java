package com.epam.kazhamiakin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestStringUtils {

	@Test
	void testPositive() {
		assertTrue(StringUtils.isPositiveNumber("1"));
	}
	
	@Test
	void testNotPositive() {
		assertFalse(StringUtils.isPositiveNumber("-1"));
	}
	
}
