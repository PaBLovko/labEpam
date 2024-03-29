package com.epam.esm.hateoas.impl;

import com.epam.esm.User;
import com.epam.esm.controller.UserController;
import com.epam.esm.hateoas.Hateoas;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserHateoas implements Hateoas<User> {
    @Override
    public void createHateoas(User user) {
        if (user.getLinks().isEmpty()) {
            user.add(linkTo(methodOn(UserController.class).findUserById(String.valueOf(user.getId()))).withSelfRel());
        }
    }
}
