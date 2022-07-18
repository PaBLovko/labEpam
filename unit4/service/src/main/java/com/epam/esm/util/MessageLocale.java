package com.epam.esm.util;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Locale;

@AllArgsConstructor
public enum MessageLocale {
    RU("ru", "RU"),
    EN("en", "US");

    @Getter
    private final String language;
    @Getter
    private final String country;

    public static Locale defineLocale(String localeStr) {
        if (localeStr != null && !localeStr.isEmpty()) {
            for (MessageLocale locale : MessageLocale.values()) {
                if (locale.country.equalsIgnoreCase(localeStr) || locale.language.equalsIgnoreCase(localeStr) ||
                        localeStr.equalsIgnoreCase(locale.toString())) {
                    return new Locale(locale.language, locale.country);
                }
            }
        }
        return new Locale(EN.language, EN.country);
    }

    @Override
    public String toString() {
        return language + "_" + country;
    }
}
