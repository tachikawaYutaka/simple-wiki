package com.wakabatimes.simplewiki.app.domain.aggregates.root_page;

import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.page.PageId;
import com.wakabatimes.simplewiki.app.domain.model.page.PageName;

public interface RootPageRepository {
    /**
     *
     * @param pageId
     * @return
     */
    RootPage getByPageId(PageId pageId);

    /**
     *
     * @param menuId
     * @param pageName
     * @return
     */
    RootPage getRootPageByName(MenuId menuId, PageName pageName);
}
