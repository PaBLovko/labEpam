package com.epam.esm.validator;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GiftCertificateTest {
    private static GiftCertificate giftCertificate;

    @BeforeAll
    void init() {
        giftCertificate = new GiftCertificate(2, "Sand", "Yellow sand", new BigDecimal("2"), 24,
                LocalDateTime.of(2020, 5, 5, 23, 42, 12),
                null, new HashSet<>());
    }

    @Test
    void isGiftCertificateCreationFormValidTest() {
        giftCertificate.setCreateDate(null);
        boolean actual = GiftCertificateValidator.isGiftCertificateCreationFormValid(giftCertificate);
        assertTrue(actual);
    }

    @Test
    void isGiftCertificateCreationFormNotValidTest() {
        boolean actual = GiftCertificateValidator.isGiftCertificateCreationFormValid(giftCertificate);
        assertFalse(actual);
    }

    @Test
    void isDurationValidTest() {
        boolean actual = GiftCertificateValidator.isDurationValid(giftCertificate.getDuration());
        assertTrue(actual);
    }

    @Test
    void isDurationNotValidTest() {
        boolean actual = GiftCertificateValidator.isDurationValid(0);
        assertFalse(actual);
    }

    @Test
    void isNameValidTest() {
        boolean actual = GiftCertificateValidator.isNameValid(giftCertificate.getName());
        assertTrue(actual);
    }

    @Test
    void isNameNotValidTest() {
        boolean actual = GiftCertificateValidator.isNameValid("<html><body><canvas id=”myCanv” ></canvas> <script>");
        assertFalse(actual);
    }

    @Test
    void isDescriptionValidTest() {
        boolean actual = GiftCertificateValidator.isDescriptionValid(giftCertificate.getDescription());
        assertTrue(actual);
    }

    @Test
    void isDescriptionNotValidTest() {
        boolean actual = GiftCertificateValidator.isDescriptionValid("");
        assertFalse(actual);
    }

    @Test
    void isPriceValidTest() {
        boolean actual = GiftCertificateValidator.isPriceValid(giftCertificate.getPrice());
        assertTrue(actual);
    }

    @Test
    void isPriceNotValidTest() {
        boolean actual = GiftCertificateValidator.isPriceValid(BigDecimal.valueOf(-12.24));
        assertFalse(actual);
    }

    @Test
    void areGiftCertificateTagsValidTest() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(1, "War"));
        tags.add(new Tag(2, "Happy"));
        tags.add(new Tag(3, "Cold"));
        tags.add(new Tag(4, "Love"));
        boolean actual = GiftCertificateValidator.areGiftCertificateTagsValid(tags);
        assertTrue(actual);
    }

    @Test
    void areGiftCertificateTagsNotValidTest() {
        boolean actual = GiftCertificateValidator.areGiftCertificateTagsValid(giftCertificate.getTags());
        assertFalse(actual);
    }

    @Test
    void areGiftCertificateTagsValidForCreationValidTest() {
        boolean actual = GiftCertificateValidator.areGiftCertificateTagsValidForCreation(giftCertificate.getTags());
        assertTrue(actual);
    }

    @Test
    void areGiftCertificateTagsValidForCreationNotValidTest() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag());
        tags.add(new Tag(null));
        tags.add(new Tag(3, "<>"));
        tags.add(new Tag(4, "''"));
        boolean actual = GiftCertificateValidator.areGiftCertificateTagsValidForCreation(tags);
        assertFalse(actual);
    }
}
