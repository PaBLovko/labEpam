package com.epam.esm.impl;

import com.epam.esm.User;
import com.epam.esm.UserRole;
import com.epam.esm.api.UserDao;
import com.epam.esm.constant.error.ErrorCode;
import com.epam.esm.constant.error.ErrorName;
import com.epam.esm.exception.InformationAccessException;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.util.UserDetailsConverter;
import com.epam.esm.validator.UserValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserDao<User> dao;

    @Mock
    private UserValidator userValidator;

    @Mock
    private PasswordEncoder encryptor;

    @Mock
    private UserDetailsConverter userDetailsConverter;

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findByIdTest() {
        User expected = new User(2, "Pavel", "Kazhamiakin", "pavel@gmail.com",
                "$2y$12$XRUTT3VBVCzf64HS.fCcse0QhSiFZlfTb7E8XZ6iBzTjvPyhC8h.W", UserRole.USER,true);
        Mockito.when(dao.findById(2)).thenReturn(Optional.of(expected));
        Mockito.when(dao.findByEmail("pavel@gmail.com")).thenReturn(Optional.of(expected));
        User actual = service.findById("2", "pavel@gmail.com");
        assertEquals(expected, actual);
    }

    @Test
    void findAllTest() {
        List<User> expected = new ArrayList<>();
        Mockito.when(dao.findAll(1, 5)).thenReturn(expected);
        List<User> actual = service.findAll(1, 5);
        assertEquals(expected, actual);
    }

    @Test()
    void findByIdThrowResourceNotFoundTest() {
        Mockito.when(dao.findById(11)).thenReturn(Optional.empty());
        Mockito.when(dao.findByEmail("pavel@gmail.com")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.findById("11", "pavel@gmail.com"));
    }

    @Test()
    void findByIdThrowInvalidFieldExceptionTest() {
        User user = new User(2, "Pavel", "Kazhamiakin", "pavel@gmail.com",
                "$2y$12$XRUTT3VBVCzf64HS.fCcse0QhSiFZlfTb7E8XZ6iBzTjvPyhC8h.W", UserRole.USER,true);
        Mockito.when(dao.findByEmail("pavel@gmail.com")).thenReturn(Optional.of(user));
        assertThrows(InvalidFieldException.class, () -> service.findById("test", "pavel@gmail.com"));
    }

    @Test
    void insertTest() {
        User user = new User(2, "Pavel", "Kazhamiakin", "pavel@gmail.com",
                "$2y$12$XRUTT3VBVCzf64HS.fCcse0QhSiFZlfTb7E8XZ6iBzTjvPyhC8h.W", UserRole.USER,true);
        long expected = user.getId();
        Mockito.when(userValidator.isUserValid(user)).thenReturn(true);
        Mockito.when(dao.findByEmail("pavel@gmail.com")).thenReturn(Optional.empty());
        Mockito.when(encryptor.encode("password")).
                thenReturn("$2y$12$XRUTT3VBVCzf64HS.fCcse0QhSiFZlfTb7E8XZ6iBzTjvPyhC8h.W");
        Mockito.when(dao.insert(user)).thenReturn(user.getId());
        long actual = service.insert(user);
        assertEquals(expected, actual);
    }

    @Test()
    void findByIdResourceNotFoundThrowTest() {
        User user = new User(2, "Pavel", "Kazhamiakin", "pavel@gmail.com",
                "$2y$12$XRUTT3VBVCzf64HS.fCcse0QhSiFZlfTb7E8XZ6iBzTjvPyhC8h.W", UserRole.USER,true);
        Mockito.when(dao.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(dao.findByEmail("error@gmail.com")).thenReturn(Optional.of(user));
        assertThrows(InformationAccessException.class, () ->
                service.findById(String.valueOf(user.getId()), "error@gmail.com"));
    }

    @Test()
    void loadUserByUsernameResourceNotFoundThrowTest() {
        Mockito.when(dao.findByEmail("error")).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> service.loadUserByUsername("error"));
    }

    @Test()
    void loadUserByUsernameUsernameNotFoundThrowTest() {
        User user = new User(2, "Pavel", "Kazhamiakin", "pavel@gmail.com",
                "$2y$12$XRUTT3VBVCzf64HS.fCcse0QhSiFZlfTb7E8XZ6iBzTjvPyhC8h.W", UserRole.USER,true);
        Mockito.when(dao.findByEmail("pavel@gmail.com")).thenReturn(Optional.of(user));
        Mockito.when(userDetailsConverter.convert(user)).thenThrow(UsernameNotFoundException.class);
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("pavel@gmail.com"));
    }
}
