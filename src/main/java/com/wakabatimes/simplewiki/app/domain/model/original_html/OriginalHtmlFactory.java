package com.wakabatimes.simplewiki.app.domain.model.original_html;

import java.util.UUID;

public class OriginalHtmlFactory {
    public static OriginalHtml create(OriginalHtmlBody originalHtmlBody){
        OriginalHtmlId originalHtmlId = new OriginalHtmlId(UUID.randomUUID().toString());
        return new OriginalHtml(originalHtmlId, originalHtmlBody);
    }
}
