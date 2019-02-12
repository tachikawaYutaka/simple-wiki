package com.wakabatimes.simplewiki.app.interfaces.original_style.dto;

import com.wakabatimes.simplewiki.app.domain.model.original_style.OriginalStyle;
import lombok.Data;

@Data
public class OriginalStyleResponseDto {
    private String id;
    private String body;

    public OriginalStyleResponseDto(OriginalStyle originalStyle){
        this.id = originalStyle.getOriginalStyleId().getValue();
        this.body = originalStyle.getOriginalStyleBody().getValue();
    }
}
