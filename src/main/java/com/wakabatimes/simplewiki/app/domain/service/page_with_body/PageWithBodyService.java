package com.wakabatimes.simplewiki.app.domain.service.page_with_body;

import com.wakabatimes.simplewiki.app.domain.aggregates.page_with_body.PageWithBodies;
import com.wakabatimes.simplewiki.app.domain.model.search.Search;

public interface PageWithBodyService {
    /**
     * 検索
     * @param search
     * @return
     */
    PageWithBodies search(Search search);
}
