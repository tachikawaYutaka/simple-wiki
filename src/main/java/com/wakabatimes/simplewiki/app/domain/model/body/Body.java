package com.wakabatimes.simplewiki.app.domain.model.body;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * root entity
 */
@Slf4j
@EqualsAndHashCode
public class Body {
    @Getter
    @NonNull
    BodyId bodyId;
    @Getter
    @NonNull
    BodyContent bodyContent;
    @Getter
    @NonNull
    BodyHtml bodyHtml;
    @Getter
    @NonNull
    BodyType bodyType;

    public Body(BodyId bodyId, BodyContent bodyContent,BodyHtml bodyHtml,BodyType bodyType) {
        this.bodyId = bodyId;
        this.bodyContent = bodyContent;
        this.bodyHtml = bodyHtml;
        this.bodyType = bodyType;
    }

    public boolean isCurrent(Body body) {
        if(body.getBodyType().equals(BodyType.CURRENT)){
            return true;
        }
        return false;
    }
}
