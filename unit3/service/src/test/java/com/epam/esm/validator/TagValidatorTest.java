package com.epam.esm.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TagValidatorTest {
    @Test
    void isNameValidTest1() {
        boolean actual = TagValidator.isNameValid("#validName");
        assertTrue(actual);
    }

    @Test
    void isNameValidTest2() {
        boolean actual = TagValidator.isNameValid("");
        assertFalse(actual);
    }
}
