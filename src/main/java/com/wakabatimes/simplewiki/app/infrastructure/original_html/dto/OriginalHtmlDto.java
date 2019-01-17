package com.wakabatimes.simplewiki.app.infrastructure.original_html.dto;

import com.wakabatimes.simplewiki.app.domain.model.original_html.OriginalHtml;
import lombok.Data;

@Data
public class OriginalHtmlDto {
    private String id;
    private String body;

    public OriginalHtmlDto() {
    }

    public OriginalHtmlDto(OriginalHtml originalHtml) {
        this.id = originalHtml.getOriginalHtmlId().getValue();
        this.body = originalHtml.getOriginalHtmlBody().getValue();
    }
}
