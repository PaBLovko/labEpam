package com.epam.unit2.service.impl;

import com.epam.unit2.dao.api.TagDao;
import com.epam.unit2.model.bean.Tag;
import com.epam.unit2.service.api.TagService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagServiceImplTest {
    private TagService<Tag> service;
    @Mock
    private TagDao<Tag> dao;


    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
        service = new TagServiceImpl(dao);
    }

    @Test
    void insertTest() {
        Tag tag = new Tag("#new");
        Mockito.when(dao.insert(tag)).thenReturn(true);
        Mockito.when(dao.findByName(Mockito.anyString())).thenReturn(Optional.empty());
        boolean actual = service.insert(tag);
        assertTrue(actual);
    }

    @Test
    void deleteTest() {
        Mockito.when(dao.delete(Mockito.anyLong())).thenReturn(true);
        boolean actual = service.delete("5");
        assertTrue(actual);
    }

    @Test
    void findByIdTest() {
        Tag expected = new Tag("#cool");
        Mockito.when(dao.findById(Mockito.anyLong())).thenReturn(Optional.of(expected));
        Tag actual = service.findById("12");
        assertEquals(expected, actual);
    }
}
