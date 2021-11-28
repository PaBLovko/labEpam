package com.epam.kazhamiakin;

import com.epam.kazhamiakin.StringUtils;

import java.util.Arrays;

public class Utils {

	public static boolean isAllPositiveNumbers(String... strs) {
		return Arrays.stream(strs).allMatch(StringUtils::isPositiveNumber);
	}
}
