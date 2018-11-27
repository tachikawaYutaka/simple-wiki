package com.wakabatimes.simplewiki.app.domain.model.user;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.theInstance;
import static org.junit.Assert.assertThat;

public class UserRoleTest {
    @Test
    public void testGetById() {
        assertThat(UserRole.getById(0), theInstance(UserRole.ROLE_ADMIN));
        assertThat(UserRole.getById(1), theInstance(UserRole.ROLE_USER));
    }

    @Test
    public void testValues() {
        assertThat(UserRole.values(), is(new UserRole[] {  UserRole.ROLE_ADMIN ,UserRole.ROLE_USER}));
    }

    @Test
    public void testValueOf() {
        assertThat(UserRole.valueOf("ROLE_USER"), theInstance(UserRole.ROLE_USER));
        assertThat(UserRole.valueOf("ROLE_ADMIN"), theInstance(UserRole.ROLE_ADMIN));
    }
}
