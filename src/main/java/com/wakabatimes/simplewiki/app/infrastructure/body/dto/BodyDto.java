package com.wakabatimes.simplewiki.app.infrastructure.body.dto;

import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import lombok.Data;

import java.util.Date;

@Data
public class BodyDto {
    private String id;
    private String content;
    private String html;
    private Integer type;
    private Date created;

    public BodyDto(){

    }

    public BodyDto(Body body) {
        this.id = body.getBodyId().getValue();
        this.content = body.getBodyContent().getValue();
        this.html = body.getBodyHtml().getValue();
        this.type = body.getBodyType().getId();
        this.created = body.getBodyCreatedDate().getValue();
    }
}
