package com.wakabatimes.simplewiki.app.domain.service.root_page;

import com.wakabatimes.simplewiki.app.domain.aggregates.root_page.RootPage;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageId;
import com.wakabatimes.simplewiki.app.domain.model.page.PageName;

public interface RootPageService {
    /**
     * メニュー直下のページ保存
     * @param rootPage
     */
    void save(RootPage rootPage);

    /**
     * rootページIDからの参照
     * @param pageId
     * @return
     */
    RootPage getByPageId(PageId pageId);


    /**
     * ページ名からのルートページの検索
     * @param pageName
     * @return
     */
    RootPage getRootPageByName(MenuId menuId, PageName pageName);

}
