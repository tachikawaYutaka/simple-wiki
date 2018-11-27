package com.wakabatimes.simplewiki.app.domain.model.page;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.theInstance;
import static org.junit.Assert.assertThat;

public class PageTypeTest {
    @Test
    public void testGetById() {
        assertThat(PageType.getById(0), theInstance(PageType.ROOT));
        assertThat(PageType.getById(1), theInstance(PageType.BRANCH));
    }

    @Test
    public void testValues() {
        assertThat(PageType.values(), is(new PageType[] {  PageType.ROOT ,PageType.BRANCH}));
    }

    @Test
    public void testValueOf() {
        assertThat(PageType.valueOf("ROOT"), theInstance(PageType.ROOT));
        assertThat(PageType.valueOf("BRANCH"), theInstance(PageType.BRANCH));
    }
}
