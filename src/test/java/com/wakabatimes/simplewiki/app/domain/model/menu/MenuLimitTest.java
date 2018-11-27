package com.wakabatimes.simplewiki.app.domain.model.menu;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsSame.theInstance;

public class MenuLimitTest {
    @Test
    public void testGetById() {
        assertThat(MenuLimit.getById(0), theInstance(MenuLimit.PUBLIC));
        assertThat(MenuLimit.getById(1), theInstance(MenuLimit.PRIVATE));
    }

    @Test
    public void testValues() {
        assertThat(MenuLimit.values(), is(new MenuLimit[] {  MenuLimit.PUBLIC ,MenuLimit.PRIVATE}));
    }

    @Test
    public void testValueOf() {
        assertThat(MenuLimit.valueOf("PUBLIC"), theInstance(MenuLimit.PUBLIC));
        assertThat(MenuLimit.valueOf("PRIVATE"), theInstance(MenuLimit.PRIVATE));
    }
}
