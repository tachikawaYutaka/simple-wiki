package com.wakabatimes.simplewiki.app.domain.service.original_html;

import com.wakabatimes.simplewiki.app.domain.model.original_html.OriginalHtml;

public interface OriginalHtmlService {
    /**
     * 独自HTMLの保存
     * @param originalHtml
     */
    void save(OriginalHtml originalHtml);

    /**
     * 独自HTMLの更新
     * @param originalHtml
     */
    void update(OriginalHtml originalHtml);

    /**
     * 独自HTMLの削除
     */
    void delete();

    /**
     * 存在確認
     * @return
     */
    boolean exist();

    /**
     * 独自HTMLの照会
     * @return
     */
    OriginalHtml get();
}
