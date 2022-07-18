package com.epam.esm.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class TagValidator {
    private static final Pattern NAME_PATTERN = Pattern.compile("[\\w\\s\\p{Punct}]{1,256}");

    public boolean isNameValid(String name) {
        return name != null && NAME_PATTERN.matcher(name).matches();
    }
}
