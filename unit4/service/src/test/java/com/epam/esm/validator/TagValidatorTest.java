package com.epam.esm.validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagValidatorTest {

    @InjectMocks
    private TagValidator tagValidator;

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void isNameValidTest1() {
        boolean actual = tagValidator.isNameValid("#validName");
        assertTrue(actual);
    }

    @Test
    void isNameValidTest2() {
        boolean actual = tagValidator.isNameValid("");
        assertFalse(actual);
    }
}
