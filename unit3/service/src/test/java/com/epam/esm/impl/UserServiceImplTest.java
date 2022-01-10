package com.epam.esm.impl;

import com.epam.esm.User;
import com.epam.esm.api.UserDao;
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
        User expected = new User(1, "Alice", "Green", "alice@gmail.com");
        Mockito.when(dao.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(expected));
        User actual = service.findById("11");
        assertEquals(expected, actual);
    }

    @Test
    void findAllTest() {
        List<User> expected = new ArrayList<>();
        Mockito.when(dao.findAll(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(expected);
        List<User> actual = service.findAll(1, 5);
        assertEquals(expected, actual);
    }
}
