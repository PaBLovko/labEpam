package com.epam.esm.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;

import static com.epam.esm.util.MessageLocale.RU;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MessageLocaleTest {

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void defineLocaleTest1() {
        Locale expected = new Locale("ru", "RU");
        Locale actual = MessageLocale.defineLocale("RU");
        assertEquals(expected, actual);
    }

    @Test
    void defineLocaleTest2() {
        Locale expected = new Locale("en", "US");
        Locale actual = MessageLocale.defineLocale("US");
        assertEquals(expected, actual);
    }

    @Test
    void defineLocaleTest3() {
        Locale expected = new Locale("en", "US");
        Locale actual = MessageLocale.defineLocale("Ur");
        assertEquals(expected, actual);
    }

    @Test
    void defineLocaleTest4() {
        Locale expected = new Locale("en", "US");
        Locale actual = MessageLocale.defineLocale("en");
        assertEquals(expected, actual);
    }

    @Test
    void defineLocaleTest5() {
        Locale expected = new Locale("ru", "ru");
        Locale actual = MessageLocale.defineLocale("ru");
        assertEquals(expected, actual);
    }

    @Test
    void toStringTest(){
        String expected = "ru_RU";
        Locale locale = new Locale("ru", "RU");
        String actual = locale.toString();
        assertEquals(expected, actual);
    }

    @Test
    void getCountryTest(){
        String expected = "RU";
        Locale locale = new Locale("ru", "RU");
        String actual = locale.getCountry();
        assertEquals(expected, actual);
    }

    @Test
    void getLanguageTest(){
        String expected = "ru";
        Locale locale = new Locale("ru", "RU");
        String actual = locale.getLanguage();
        assertEquals(expected, actual);
    }
}
