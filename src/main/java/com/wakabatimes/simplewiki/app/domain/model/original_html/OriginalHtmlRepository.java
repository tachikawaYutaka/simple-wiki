package com.wakabatimes.simplewiki.app.domain.model.original_html;

public interface OriginalHtmlRepository {
    void save(OriginalHtml originalHtml);

    void update(OriginalHtml originalHtml);

    void delete();

    boolean exist();

    OriginalHtml get();
}
