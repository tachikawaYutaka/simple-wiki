package com.wakabatimes.simplewiki.app.domain.model.body;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.theInstance;
import static org.junit.Assert.assertThat;

public class BodyTypeTest {
    @Test
    public void testGetById() {
        assertThat(BodyType.getById(0), theInstance(BodyType.CURRENT));
        assertThat(BodyType.getById(1), theInstance(BodyType.ARCHIVE));
        assertThat(BodyType.getById(2), theInstance(BodyType.ARCHIVE));
    }

    @Test
    public void testValues() {
        assertThat(BodyType.values(), is(new BodyType[] {  BodyType.CURRENT ,BodyType.ARCHIVE}));
    }

    @Test
    public void testValueOf() {
        assertThat(BodyType.valueOf("CURRENT"), theInstance(BodyType.CURRENT));
        assertThat(BodyType.valueOf("ARCHIVE"), theInstance(BodyType.ARCHIVE));
    }
}
