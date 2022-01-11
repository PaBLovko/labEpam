package com.epam.esm.impl;

import com.epam.esm.User;
import com.epam.esm.constant.ErrorAttribute;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceDuplicateException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.api.TagDao;
import com.epam.esm.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagServiceImplTest {
    @InjectMocks
    private TagServiceImpl service;
    @Mock
    private TagDao<Tag> dao;
    @Mock
    private UserServiceImpl userService;


    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
        service = new TagServiceImpl(dao, userService);
    }

    @Test
    void insertTest() {
        Tag tag = new Tag("#new");
        Mockito.when(dao.insert(tag)).thenReturn(tag.getId());
        Mockito.when(dao.findByName(tag.getName())).thenReturn(Optional.empty());
        long actual = service.insert(tag);
        long expected = tag.getId();
        assertEquals(expected, actual);
    }

    @Test
    void deleteTest() {
        Mockito.when(dao.delete(5)).thenReturn(true);
        boolean actual = service.delete("5");
        assertTrue(actual);
    }

    @Test
    void findByIdTest() {
        Tag expected = new Tag(1,"#cool");
        Mockito.when(dao.findById(1)).thenReturn(Optional.of(expected));
        Tag actual = service.findById("1");
        assertEquals(expected, actual);
    }

    @Test()
    void deleteThrowTest() {
        assertThrows(InvalidFieldException.class, () -> service.delete(""));
    }

    @Test()
    void insertInvalidThrowTest() {
        Mockito.when(dao.findByName(null)).thenReturn(Optional.empty());
        assertThrows(InvalidFieldException.class, () -> service.insert(new Tag(1,null)));
    }

    @Test()
    void insertResourceDuplicateThrowTest() {
        Tag tag = new Tag(1, "#funny");
        Mockito.when(dao.findByName(tag.getName())).thenReturn(Optional.of(tag));
        assertThrows(ResourceDuplicateException.class, () -> service.insert(tag));
    }

    @Test()
    void findByIdInvalidThrowTest() {
        assertThrows(InvalidFieldException.class, () -> service.findById(null));
    }

    @Test()
    void findByIdResourceNotFoundThrowTest() {
        Mockito.when(dao.findById(0)).thenThrow(new ResourceNotFoundException(ErrorAttribute.TAG_ERROR_CODE,
                ErrorAttribute.RESOURCE_NOT_FOUND_ERROR, "0"));
        assertThrows(ResourceNotFoundException.class, () -> service.findById(Long.toString(0)));
    }

    @Test()
    void findByNameInvalidThrowTest() {
        assertThrows(InvalidFieldException.class, () -> service.findByName(null));
    }

    @Test()
    void findByNameResourceNotFoundThrowTest() {
        Mockito.when(dao.findByName("name")).thenThrow(new ResourceNotFoundException(ErrorAttribute.TAG_ERROR_CODE,
                ErrorAttribute.RESOURCE_NOT_FOUND_ERROR, "name"));
        assertThrows(ResourceNotFoundException.class, () -> service.findByName("name"));
    }

    @Test()
    void findMostUsedTagOfUserWithHighestCostOfAllOrdersTest() {
        Tag expected = new Tag(1,"#cool");
        User user = new User(1, "Pablo", "Escobar", "pablo@gmail.com");
        Mockito.when(dao.findMostUsedTagOfUserWithHighestCostOfAllOrders(1)).thenReturn(Optional.of(expected));
        Mockito.when(userService.findById("1")).thenReturn(user);
        Tag actual = service.findMostUsedTagOfUserWithHighestCostOfAllOrders("1");
        assertEquals(expected, actual);
    }

    @Test()
    void findMostUsedTagOfUserWithHighestCostOfAllOrdersThrowTest() {
        Mockito.when(dao.findMostUsedTagOfUserWithHighestCostOfAllOrders(11)).
                thenThrow(new RuntimeException());
        Mockito.when(userService.findById("11")).thenThrow(new ResourceNotFoundException(
                ErrorAttribute.TAG_ERROR_CODE, ErrorAttribute.RESOURCE_NOT_FOUND_ERROR, "11"));
        assertThrows(ResourceNotFoundException.class,
                () -> service.findMostUsedTagOfUserWithHighestCostOfAllOrders("11"));
    }

}
