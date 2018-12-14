package com.wakabatimes.simplewiki.app.domain.model.body;

import java.util.UUID;

public class BodyFactory {
    public static Body create(BodyContent bodyContent, BodyHtml bodyHtml){
        BodyId bodyId = new BodyId(UUID.randomUUID().toString());
        BodyType bodyType = BodyType.CURRENT;
        return new Body(bodyId, bodyContent,bodyHtml, bodyType);
    }
}
