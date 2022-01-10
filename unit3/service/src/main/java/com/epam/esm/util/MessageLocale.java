package com.epam.esm.util;

import lombok.*;

import java.util.Locale;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessageLocale {
    private static Locale current;
    static {
        current = new Locale(CustomLocale.EN.language, CustomLocale.EN.country);
    }

    public static Locale getCurrent() {
        return current;
    }


    public static void changeLocale() {
        current = current.getLanguage().equals(CustomLocale.EN.language) ? new Locale(CustomLocale.RU.language,
                CustomLocale.RU.country) : new Locale(CustomLocale.EN.language, CustomLocale.EN.country);
    }

    @AllArgsConstructor
    private enum CustomLocale {
        RU("ru", "RU"),
        EN("en", "US");
        @Getter
        private final String language;
        @Getter
        private final String country;
    }
}
