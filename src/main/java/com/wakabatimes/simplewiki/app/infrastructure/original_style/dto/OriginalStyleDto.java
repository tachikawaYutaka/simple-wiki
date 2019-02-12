package com.wakabatimes.simplewiki.app.infrastructure.original_style.dto;

import com.wakabatimes.simplewiki.app.domain.model.original_style.OriginalStyle;
import lombok.Data;

@Data
public class OriginalStyleDto {
    private String id;
    private String body;

    public OriginalStyleDto() {
    }

    public OriginalStyleDto(OriginalStyle originalStyle) {
        this.id = originalStyle.getOriginalStyleId().getValue();
        this.body = originalStyle.getOriginalStyleBody().getValue();
    }
}
