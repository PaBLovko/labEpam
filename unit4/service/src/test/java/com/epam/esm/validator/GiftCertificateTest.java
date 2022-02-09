package com.epam.esm.validator;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GiftCertificateTest {
    private GiftCertificate giftCertificate;
    @Mock
    private TagValidator tagValidator;
    @InjectMocks
    private GiftCertificateValidator giftCertificateValidator;

    @BeforeAll
    void init() {
        giftCertificate = new GiftCertificate(2, true, "Sand", "Yellow sand", new BigDecimal("2"), 24,
                LocalDateTime.of(2020, 5, 5, 23, 42, 12),
                null, new HashSet<>());
        initMocks(this);
    }

    @Test
    void isGiftCertificateCreationFormValidTest() {
        giftCertificate.setCreateDate(null);
        boolean actual = giftCertificateValidator.isGiftCertificateCreationFormValid(giftCertificate);
        assertTrue(actual);
    }

    @Test
    void isGiftCertificateCreationFormNotValidTest() {
        boolean actual = giftCertificateValidator.isGiftCertificateCreationFormValid(giftCertificate);
        assertFalse(actual);
    }

    @Test
    void isDurationValidTest() {
        boolean actual = giftCertificateValidator.isDurationValid(giftCertificate.getDuration());
        assertTrue(actual);
    }

    @Test
    void isDurationNotValidTest() {
        boolean actual = giftCertificateValidator.isDurationValid(0);
        assertFalse(actual);
    }

    @Test
    void isNameValidTest() {
        boolean actual = giftCertificateValidator.isNameValid(giftCertificate.getName());
        assertTrue(actual);
    }

    @Test
    void isNameNotValidTest() {
        boolean actual = giftCertificateValidator.isNameValid("<html><body><canvas id=”myCanv” ></canvas> <script>");
        assertFalse(actual);
    }

    @Test
    void isDescriptionValidTest() {
        boolean actual = giftCertificateValidator.isDescriptionValid(giftCertificate.getDescription());
        assertTrue(actual);
    }

    @Test
    void isDescriptionNotValidTest() {
        boolean actual = giftCertificateValidator.isDescriptionValid("");
        assertFalse(actual);
    }

    @Test
    void isPriceValidTest() {
        boolean actual = giftCertificateValidator.isPriceValid(giftCertificate.getPrice());
        assertTrue(actual);
    }

    @Test
    void isPriceNotValidTest() {
        boolean actual = giftCertificateValidator.isPriceValid(BigDecimal.valueOf(-12.24));
        assertFalse(actual);
    }

    @Test
    void areGiftCertificateTagsValidTest() {
        giftCertificate.getTags().add(new Tag(1, "War", true));
        Mockito.when(tagValidator.isNameValid("War")).thenReturn(true);
        boolean actual = giftCertificateValidator.areGiftCertificateTagsValid(giftCertificate.getTags());
        assertTrue(actual);
    }

    @Test
    void areGiftCertificateTagsNotValidTest() {
        boolean actual = giftCertificateValidator.areGiftCertificateTagsValid(giftCertificate.getTags());
        assertFalse(actual);
    }

    @Test
    void areGiftCertificateTagsValidForCreationValidTest() {
        boolean actual = giftCertificateValidator.areGiftCertificateTagsValidForCreation(giftCertificate.getTags());
        assertTrue(actual);
    }

    @Test
    void areGiftCertificateTagsValidForCreationNotValidTest() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(1, "<>", true));
        Mockito.when(tagValidator.isNameValid("<>")).thenReturn(false);
        boolean actual = giftCertificateValidator.areGiftCertificateTagsValidForCreation(tags);
        assertFalse(actual);
    }
}
