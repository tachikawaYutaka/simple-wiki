package com.wakabatimes.simplewiki.app.domain.service.root_page;

import com.wakabatimes.simplewiki.app.domain.aggregates.root_page.RootPage;

public interface RootPageService {
    /**
     * メニュー直下のページ保存
     * @param rootPage
     */
    void save(RootPage rootPage);
}
