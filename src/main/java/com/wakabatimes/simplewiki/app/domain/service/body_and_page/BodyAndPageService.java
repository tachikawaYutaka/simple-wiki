package com.wakabatimes.simplewiki.app.domain.service.body_and_page;

import com.wakabatimes.simplewiki.app.domain.aggregates.body_and_page.BodyAndPage;

public interface BodyAndPageService {
    /**
     * bodyとpageの保存
     * @param bodyAndPage
     */
    void save(BodyAndPage bodyAndPage);
}
