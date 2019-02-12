package com.wakabatimes.simplewiki.app.domain.model.original_html;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * root entity
 */
@Slf4j
@EqualsAndHashCode
public class OriginalHtml {
    @NonNull
    @Getter
    OriginalHtmlId originalHtmlId;

    @NonNull
    @Getter
    OriginalHtmlBody originalHtmlBody;

    public OriginalHtml(OriginalHtmlId originalHtmlId, OriginalHtmlBody originalHtmlBody) {
        this.originalHtmlId = originalHtmlId;
        this.originalHtmlBody = originalHtmlBody;
    }
}
