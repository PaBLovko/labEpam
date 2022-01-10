package com.epam.esm.validator;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Tag;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GiftCertificateValidator {
    private static final Pattern NAME_PATTERN = Pattern.compile("[\\w\\s\\p{Punct}]{1,256}");
    private static final Pattern DESCRIPTION_PATTERN = Pattern.compile("[\\w\\s\\p{Punct}]{1,5000}");

    public static boolean isGiftCertificateCreationFormValid(GiftCertificate giftCertificate) {
        return (isNameValid(giftCertificate.getName()) && isDescriptionValid(giftCertificate.getDescription()) &&
                isPriceValid(giftCertificate.getPrice()) && isDurationValid(giftCertificate.getDuration()) &&
                giftCertificate.getCreateDate() == null && giftCertificate.getLastUpdateDate() == null &&
                areGiftCertificateTagsValidForCreation(giftCertificate.getTags()));
    }

    public static boolean isNameValid(String name) {
        return (name != null && NAME_PATTERN.matcher(name).matches());
    }

    public static boolean isDescriptionValid(String description) {
        return (description != null && DESCRIPTION_PATTERN.matcher(description).matches());
    }

    public static boolean isPriceValid(BigDecimal price) {
        return (price != null && price.compareTo(BigDecimal.ZERO) > 0);
    }

    public static boolean isDurationValid(int duration) {
        return duration > 0;
    }

    public static boolean areGiftCertificateTagsValidForCreation(Set<Tag> tags) {
        if (tags == null || tags.isEmpty()) {
            return true;
        }
        return tags.stream().allMatch(t -> TagValidator.isNameValid(t.getName()));
    }

    public static boolean areGiftCertificateTagsValid(Set<Tag> tags) {
        if (tags == null || tags.isEmpty()) {
            return false;
        }
        return tags.stream().allMatch(t -> TagValidator.isNameValid(t.getName()));
    }
}
