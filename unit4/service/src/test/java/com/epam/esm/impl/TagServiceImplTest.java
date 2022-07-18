package com.epam.esm.impl;

import com.epam.esm.Tag;
import com.epam.esm.User;
import com.epam.esm.UserRole;
import com.epam.esm.api.TagDao;
import com.epam.esm.constant.error.ErrorCode;
import com.epam.esm.constant.error.ErrorName;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceDuplicateException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.validator.TagValidator;
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
    @Mock
    private TagValidator tagValidator;


    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
        service = new TagServiceImpl(dao, userService, tagValidator);
    }

    @Test
    void insertTest() {
        Tag tag = new Tag("#new");
        Mockito.when(dao.insert(tag)).thenReturn(tag.getId());
        Mockito.when(dao.findByName(tag.getName())).thenReturn(Optional.empty());
        Mockito.when(tagValidator.isNameValid("#new")).thenReturn(true);
        long actual = service.insert(tag);
        long expected = tag.getId();
        assertEquals(expected, actual);
    }

    @Test
    void deleteTest() {
        Tag tag = new Tag(1, "Fax", false);
        Mockito.when(dao.delete(1)).thenReturn(true);
        service.delete("1", false);
        boolean actual = tag.isAvailable();
        assertFalse(actual);
    }

    @Test
    void findByIdTest() {
        Tag expected = new Tag(1,"#cool", true);
        Mockito.when(dao.findById(1)).thenReturn(Optional.of(expected));
        Tag actual = service.findById("1");
        assertEquals(expected, actual);
    }

    @Test
    void findByNameTest() {
        Tag expected = new Tag(1,"#cool", true);
        Mockito.when(dao.findByName("#cool")).thenReturn(Optional.of(expected));
        Mockito.when(tagValidator.isNameValid("#cool")).thenReturn(true);
        Tag actual = service.findByName("#cool");
        assertEquals(expected, actual);
    }

    @Test()
    void deleteInvalidFieldThrowTest() {
        assertThrows(InvalidFieldException.class, () -> service.delete("", true));
    }

    @Test()
    void deleteResourceNotFoundThrowTest() {
        Mockito.when(dao.delete(2)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.delete("2", false));
    }


    @Test()
    void insertInvalidThrowTest() {
        Mockito.when(dao.findByName(null)).thenReturn(Optional.empty());
        assertThrows(InvalidFieldException.class, () -> service.insert(new Tag(1,null, true)));
    }

    @Test()
    void insertResourceDuplicateThrowTest() {
        Tag tag = new Tag(1, "#funny", true);
        Mockito.when(dao.findByName(tag.getName())).thenReturn(Optional.of(tag));
        Mockito.when(tagValidator.isNameValid("#funny")).thenReturn(true);
        assertThrows(ResourceDuplicateException.class, () -> service.insert(tag));
    }

    @Test()
    void findByIdInvalidThrowTest() {
        assertThrows(InvalidFieldException.class, () -> service.findById(null));
    }

    @Test()
    void findByIdResourceNotFoundThrowTest() {
        Mockito.when(dao.findById(0)).thenThrow(new ResourceNotFoundException(ErrorCode.TAG,
                ErrorName.RESOURCE_NOT_FOUND, "0"));
        assertThrows(ResourceNotFoundException.class, () -> service.findById(Long.toString(0)));
    }

    @Test()
    void findByNameInvalidThrowTest() {
        assertThrows(InvalidFieldException.class, () -> service.findByName(null));
    }

    @Test()
    void findByNameResourceNotFoundThrowTest() {
        Mockito.when(dao.findByName("name")).thenThrow(new ResourceNotFoundException(ErrorCode.TAG,
                ErrorName.RESOURCE_NOT_FOUND, "name"));
        Mockito.when(tagValidator.isNameValid("name")).thenReturn(true);
        assertThrows(ResourceNotFoundException.class, () -> service.findByName("name"));
    }

    @Test()
    void findMostUsedTagOfUserWithHighestCostOfAllOrdersTest() {
        Tag expected = new Tag(1,"#cool", true);
        User user = new User(2, "Pavel", "Kazhamiakin", "pavel@gmail.com",
                "$2y$12$XRUTT3VBVCzf64HS.fCcse0QhSiFZlfTb7E8XZ6iBzTjvPyhC8h.W", UserRole.USER,true);
        Mockito.when(dao.findMostUsedTagOfUserWithHighestCostOfAllOrders(2)).thenReturn(Optional.of(expected));
        Mockito.when(userService.findById("2", "pavel@gmail.com")).thenReturn(user);
        Tag actual = service.findMostUsedTagOfUserWithHighestCostOfAllOrders("2", "pavel@gmail.com");
        assertEquals(expected, actual);
    }

    @Test()
    void findMostUsedTagOfUserWithHighestCostOfAllOrdersThrowTest() {
        Mockito.when(dao.findMostUsedTagOfUserWithHighestCostOfAllOrders(11)).
                thenThrow(new RuntimeException());
        Mockito.when(userService.findById("1100000", "pave@gmail.com")).thenThrow(new ResourceNotFoundException(
                ErrorCode.TAG, ErrorName.RESOURCE_NOT_FOUND, "11"));
        assertThrows(ResourceNotFoundException.class,
                () -> service.findMostUsedTagOfUserWithHighestCostOfAllOrders("1100000", "pave@gmail.com"));
    }

    @Test
    void updateAvailabilityTest() {
        Tag tag = new Tag(1,"#cool", false);
        Mockito.when(dao.findById(tag.getId())).thenReturn(Optional.of(tag));
        service.updateAvailability(String.valueOf(tag.getId()), true);
        assertTrue(tag.isAvailable());
    }

    @Test()
    void updateAvailabilityThrowTest() {
        assertThrows(InvalidFieldException.class, () -> service.updateAvailability("error", true));
    }
}
