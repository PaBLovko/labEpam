package com.epam.esm.impl;

import com.epam.esm.User;
import com.epam.esm.api.UserDao;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findById() {
        User expected = new User(1, "Pablo", "Escobar", "pablo@gmail.com");
        Mockito.when(dao.findById(1)).thenReturn(Optional.of(expected));
        User actual = service.findById("1");
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
        assertThrows(ResourceNotFoundException.class, () -> service.findById("11"));
    }

    @Test()
    void findByIdThrowInvalidFieldExceptionTest() {
        assertThrows(InvalidFieldException.class, () -> service.findById("test"));
    }
}
