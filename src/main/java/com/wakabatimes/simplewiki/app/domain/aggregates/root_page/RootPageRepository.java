package com.wakabatimes.simplewiki.app.domain.aggregates.root_page;

import com.wakabatimes.simplewiki.app.domain.model.page.PageId;

public interface RootPageRepository {
    /**
     *
     * @param pageId
     * @return
     */
    RootPage getByPageId(PageId pageId);
}
