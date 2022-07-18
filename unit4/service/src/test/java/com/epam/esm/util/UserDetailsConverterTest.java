package com.epam.esm.util;

import com.epam.esm.User;
import com.epam.esm.UserRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.security.core.userdetails.UserDetails;


import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDetailsConverterTest {
    private UserDetailsConverter userDetailsConverter;

    @BeforeAll
    public void init() {
        userDetailsConverter = new UserDetailsConverter();
    }

    @Test
    void convertValidTest() {
        UserDetails expected = new org.springframework.security.core.userdetails.User("pavel@gmail.com",
                "$2y$12$XRUTT3VBVCzf64HS.fCcse0QhSiFZlfTb7E8XZ6iBzTjvPyhC8h.W",
                true, true, true, true, UserRole.USER.getAuthorities());
        User user = new User(2, "Pavel", "Kazhamiakin", "pavel@gmail.com",
                "$2y$12$XRUTT3VBVCzf64HS.fCcse0QhSiFZlfTb7E8XZ6iBzTjvPyhC8h.W", UserRole.USER,true);
        UserDetails actual = userDetailsConverter.convert(user);
        assertEquals(expected, actual);
    }

}
