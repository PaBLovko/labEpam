package com.epam.esm.impl;


import com.epam.esm.User;
import com.epam.esm.api.UserDao;
import com.epam.esm.config.DataSourceConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DirtiesContext
@ContextConfiguration(classes = {UserDaoImpl.class, DataSourceConfig.class},
        loader = AnnotationConfigContextLoader.class)
@SpringBootTest
class UserDaoImplTest {

    @Autowired
    private UserDao<User> dao;

    @Test
    void findByIdTest() {
        Optional<User> expected = Optional.of(new User(1, "Pablo", "Escobar", "pablo@gmail.com"));
        Optional<User> actual = dao.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void findAllTest() {
        List<User> expected = new ArrayList<>();
        List<User> actual = dao.findAll(12345, 12345);
        assertEquals(expected, actual);
    }
}
