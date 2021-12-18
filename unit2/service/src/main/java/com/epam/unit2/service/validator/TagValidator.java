package com.epam.unit2.service.validator;

import java.util.regex.Pattern;

//@NoArgsConstructor(access = AccessLevel.PRIVATE);
public class TagValidator {
    private static final Pattern NAME_PATTERN = Pattern.compile("[А-Яа-я\\w\\s\\p{Punct}]{1,256}");

    private TagValidator() {
    }

    public static boolean isNameValid(String name) {
        return (name != null && NAME_PATTERN.matcher(name).matches());
    }
}
