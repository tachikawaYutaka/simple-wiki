package com.wakabatimes.simplewiki.app.domain.service.body;

import com.wakabatimes.simplewiki.app.domain.model.body.Bodies;
import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import com.wakabatimes.simplewiki.app.domain.model.body.BodyId;
import com.wakabatimes.simplewiki.app.domain.model.page.PageId;

public interface BodyService {
    /**
     * ボディの保存
     * @param body
     * @param pageId
     */
    void save(Body body, PageId pageId);

    /**
     * カレントボディの取得
     * @param pageId
     * @return
     */
    Body getCurrent(PageId pageId);

    /**
     * アーカイブの取得
     * @param pageId
     * @return
     */
    Bodies getArchive(PageId pageId);

    /**
     * ボディの参照
     * @param bodyId
     * @return
     */
    Body get(BodyId bodyId);

    /**
     * ボディの削除
     * @param body
     */
    void delete(Body body);
}
