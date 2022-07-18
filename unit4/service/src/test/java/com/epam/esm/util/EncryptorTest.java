package com.epam.esm.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EncryptorTest {

    @InjectMocks
    private Encryptor encryptor;

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void passwordEncoderValidTest() {
        PasswordEncoder passwordEncoder = encryptor.passwordEncoder();
        int expected = 60;
        int actual = passwordEncoder.encode("password").length();
        assertEquals(expected, actual);
    }

}
