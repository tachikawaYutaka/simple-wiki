package com.wakabatimes.simplewiki.app.interfaces.body.dto;

import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import lombok.Data;

@Data
public class BodyResponseDto {
    String id;
    String content;
    String html;
    Integer type;

    public BodyResponseDto(Body body){
        this.id = body.getBodyId().getValue();
        this.content = body.getBodyContent().getValue();
        this.html = body.getBodyHtml().getValue();
        this.type = body.getBodyType().getId();
    }
}
