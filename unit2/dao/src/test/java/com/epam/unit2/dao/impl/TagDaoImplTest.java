package com.epam.unit2.dao.impl;

import com.epam.unit2.dao.api.TagDao;
import com.epam.unit2.dao.config.DataSourceConfig;
import com.epam.unit2.dao.mapper.TagMapper;
import com.epam.unit2.model.bean.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TagDaoImplTest {
    private static final TagDao<Tag> dao = new TagDaoImpl(DataSourceConfig.dataSource, new TagMapper());

    @Test
    void findByNameTest() {
        Optional<Tag> expected = Optional.of(new Tag(3, "#warm"));
        Optional<Tag> actual = dao.findByName("#warm");
        assertEquals(expected, actual);
    }

    @Test
    void findByIdTest() {
        Optional<Tag> expected = Optional.empty();
        Optional<Tag> actual = dao.findById(12345);
        assertEquals(expected, actual);
    }

    @Test
    void findTagsConnectedToCertificateTest() {
        List<Tag> actual = new ArrayList<>();
        actual.add(new Tag(1, "#funny"));
        actual.add(new Tag(2, "#cool"));
        List<Tag> expected = dao.findTagsConnectedToCertificate(1);
        assertEquals(expected, actual);
    }

    @Test
    void deleteTest() {
        boolean actual = dao.delete(12345);
        assertFalse(actual);
    }
}
