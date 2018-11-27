package com.wakabatimes.simplewiki.app.domain.model.body;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BodiesTest {
    @Test
    public void add() {
        BodyContent bodyContent = new BodyContent("hogehoge");
        Body body = BodyFactory.create(bodyContent);

        BodyContent bodyContent2 = new BodyContent("hogehoge");
        Body body2 = BodyFactory.create(bodyContent2);

        Bodies bodies = new Bodies();
        bodies.add(body);
        bodies.add(body2);

        assertEquals(2, bodies.size().intValue());
    }
}
