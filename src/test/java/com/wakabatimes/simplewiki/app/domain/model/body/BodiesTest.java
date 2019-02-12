package com.wakabatimes.simplewiki.app.domain.model.body;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BodiesTest {
    @Test
    public void add() {
        BodyContent bodyContent = new BodyContent("hogehoge");
        BodyHtml bodyHtml = new BodyHtml("hogehoge");
        Body body = BodyFactory.create(bodyContent, bodyHtml);

        BodyContent bodyContent2 = new BodyContent("hogehoge");
        BodyHtml bodyHtml2 = new BodyHtml("hogehoge");
        Body body2 = BodyFactory.create(bodyContent2, bodyHtml2);

        Bodies bodies = new Bodies();
        bodies.add(body);
        bodies.add(body2);

        assertEquals(2, bodies.size().intValue());
    }

    @Test
    public void bodies(){
        BodyContent bodyContent = new BodyContent("hogehoge");
        BodyHtml bodyHtml = new BodyHtml("hogehoge");
        Body body = BodyFactory.create(bodyContent, bodyHtml);

        BodyContent bodyContent2 = new BodyContent("hogehoge");
        BodyHtml bodyHtml2 = new BodyHtml("hogehoge");
        Body body2 = BodyFactory.create(bodyContent2, bodyHtml2);

        List<Body> bodyList = new ArrayList<>();
        bodyList.add(body);
        bodyList.add(body2);
        Bodies bodies = new Bodies(bodyList);
        assertEquals(2, bodies.size().intValue());
    }

    @Test
    public void list(){
        BodyContent bodyContent = new BodyContent("hogehoge");
        BodyHtml bodyHtml = new BodyHtml("hogehoge");
        Body body = BodyFactory.create(bodyContent, bodyHtml);

        BodyContent bodyContent2 = new BodyContent("hogehoge");
        BodyHtml bodyHtml2 = new BodyHtml("hogehoge");
        Body body2 = BodyFactory.create(bodyContent2, bodyHtml2);

        Bodies bodies = new Bodies();
        bodies.add(body);
        bodies.add(body2);

        List<Body> bodyList = new ArrayList<>();
        bodyList.add(body);
        bodyList.add(body2);

        assertEquals(bodies.list(),bodyList);
    }
}
