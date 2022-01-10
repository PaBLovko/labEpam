package com.epam.esm.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagValidator {
    private static final Pattern NAME_PATTERN = Pattern.compile("[\\w\\s\\p{Punct}]{1,256}");

    public static boolean isNameValid(String name) {
        return (name != null && NAME_PATTERN.matcher(name).matches());
    }
}
