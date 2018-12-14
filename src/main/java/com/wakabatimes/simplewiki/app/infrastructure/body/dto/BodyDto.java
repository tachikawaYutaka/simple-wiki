package com.wakabatimes.simplewiki.app.infrastructure.body.dto;

import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import lombok.Data;

@Data
public class BodyDto {
    String id;
    String content;
    String html;
    Integer type;

    public BodyDto(){

    }

    public BodyDto(Body body) {
        this.id = body.getBodyId().getValue();
        this.content = body.getBodyContent().getValue();
        this.html = body.getBodyHtml().getValue();
        this.type = body.getBodyType().getId();
    }
}
