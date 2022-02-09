package com.epam.esm.impl;

import com.epam.esm.api.TagDao;
import com.epam.esm.config.DataSourceConfig;
import com.epam.esm.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext
@ContextConfiguration(classes = {TagDaoImpl.class, DataSourceConfig.class},
        loader = AnnotationConfigContextLoader.class)
@SpringBootTest
class TagDaoImplTest {

    @Autowired
    private TagDao<Tag> dao;

    @Test
    void findAllTest() {
        boolean actual = !dao.findAll(0,0).isEmpty();
        assertTrue(actual);
    }

    @Test
    void findByNameTest() {
        Optional<Tag> expected = Optional.of(new Tag(1, "#funny", true));
        Optional<Tag> actual = dao.findByName("#funny");
        assertEquals(expected, actual);
    }

    @Test
    void findByIdTest() {
        Optional<Tag> expected = Optional.empty();
        Optional<Tag> actual = dao.findById(12345);
        assertEquals(expected, actual);
    }

    @Test
    void insertTest() {
        long expected = 6;
        long actual = dao.insert(new Tag("#newtaginserttest"));
        assertEquals(expected, actual);
    }

    @Test
    void deleteTest() {
        boolean actual = dao.delete(6);
        assertTrue(actual);
    }
}
