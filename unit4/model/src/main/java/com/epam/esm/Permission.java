package com.epam.esm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Permission {
    TAGS_READ("tags:read"),
    TAGS_WRITE("tags:write"),
    TAGS_DELETE("tags:delete"),
    CERTIFICATES_READ("certificates:read"),
    CERTIFICATES_WRITE("certificates:write"),
    CERTIFICATES_EDIT("certificates:edit"),
    CERTIFICATES_DELETE("certificates:delete"),
    ALL_USERS_READ("all_users:read"),
    USERS_READ("users:read"),
    USERS_WRITE("users:write");

    @Getter
    private final String permission;
}
