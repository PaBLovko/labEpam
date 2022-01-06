package com.epam.esm.validator;

import java.util.regex.Pattern;

public class TagValidator {
    private static final Pattern NAME_PATTERN = Pattern.compile("[\\w\\s\\p{Punct}]{1,256}");

    private TagValidator() {
    }

    public static boolean isNameValid(String name) {
        return (name != null && NAME_PATTERN.matcher(name).matches());
    }
}
