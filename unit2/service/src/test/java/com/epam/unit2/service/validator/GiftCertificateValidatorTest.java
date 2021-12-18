package com.epam.unit2.service.validator;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GiftCertificateValidatorTest {
    @Test
    void isNameValidTest() {
        boolean actual = GiftCertificateValidator.isNameValid("Valid Name");
        assertTrue(actual);
    }

    @Test
    void isDescriptionValidTest() {
        boolean actual = GiftCertificateValidator.isDescriptionValid("");
        assertFalse(actual);
    }

    @Test
    void isPriceValidTest() {
        boolean actual = GiftCertificateValidator.isPriceValid(BigDecimal.valueOf(-12.24));
        assertFalse(actual);
    }
}
