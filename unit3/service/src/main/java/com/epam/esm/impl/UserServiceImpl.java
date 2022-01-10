package com.epam.esm.impl;

import com.epam.esm.User;
import com.epam.esm.api.UserDao;
import com.epam.esm.api.UserService;
import com.epam.esm.constant.ErrorAttribute;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao<User> dao;

    @Autowired
    public UserServiceImpl(UserDao<User> dao) {
        this.dao = dao;
    }

    @Override
    public User findById(String id) {
        try {
            return dao.findById(Long.parseLong(id)).orElseThrow(() -> new ResourceNotFoundException(
                    ErrorAttribute.USER_ERROR_CODE, ErrorAttribute.RESOURCE_NOT_FOUND_ERROR, id));
        } catch (NumberFormatException e) {
            throw new InvalidFieldException(ErrorAttribute.USER_ERROR_CODE, ErrorAttribute.INVALID_USER_ID_ERROR, id);
        }
    }

    @Override
    public List<User> findAll(int page, int elements) {
        return dao.findAll(page, elements);
    }
}
