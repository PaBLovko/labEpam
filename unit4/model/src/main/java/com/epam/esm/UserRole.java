package com.epam.esm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.collections4.SetUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum UserRole {
    USER(SetUtils.hashSet(Permission.TAGS_READ, Permission.CERTIFICATES_READ, Permission.USERS_READ,
            Permission.USERS_WRITE)),
    ADMIN(SetUtils.hashSet(Permission.values()));

    @Getter
    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return permissions.stream().map(p -> new SimpleGrantedAuthority(p.getPermission())).collect(Collectors.toSet());
    }
}
