package com.epam.esm.validator;

import com.epam.esm.User;
import com.epam.esm.UserRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserValidatorTest {

    @InjectMocks
    private UserValidator userValidator;

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void isNameValidTest() {
        boolean actual = userValidator.isNameValid("validName");
        assertTrue(actual);
    }

    @Test
    void isUserValidTest() {
        User user = new User(2, "Pavel", "Kazhamiakin", "pavel@gmail.com",
                "$2y$12$XRUTT3VBVCzf64HS.fCcse0QhSiFZlfTb7E8XZ6iBzTjvPyhC8h.W", UserRole.USER,true);
        boolean actual = userValidator.isUserValid(user);
        assertFalse(actual);
    }

    @Test
    void isEmailValidTest() {
        boolean actual = userValidator.isEmailValid("pavel@gmail.com");
        assertTrue(actual);
    }
}
