package com.wakabatimes.simplewiki.app.domain.aggregates.page_with_body;

import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import lombok.Getter;
import lombok.NonNull;

public class PageWithBody {
    @Getter
    @NonNull
    Page page;
    @Getter
    @NonNull
    Body body;

    public PageWithBody(Page page, Body body) {
        this.page = page;
        this.body = body;
    }
}
