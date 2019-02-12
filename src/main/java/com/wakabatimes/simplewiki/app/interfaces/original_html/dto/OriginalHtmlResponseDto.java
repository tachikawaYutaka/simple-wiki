package com.wakabatimes.simplewiki.app.interfaces.original_html.dto;

import com.wakabatimes.simplewiki.app.domain.model.original_html.OriginalHtml;
import lombok.Data;

@Data
public class OriginalHtmlResponseDto {
    private String id;
    private String body;

    public OriginalHtmlResponseDto(OriginalHtml originalHtml){
        this.id = originalHtml.getOriginalHtmlId().getValue();
        this.body = originalHtml.getOriginalHtmlBody().getValue();
    }
}
