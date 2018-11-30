package com.wakabatimes.simplewiki.app.domain.service.original_style;

import com.wakabatimes.simplewiki.app.domain.model.original_style.OriginalStyle;

public interface OriginalStyleService {
    /**
     * 独自スタイルの保存
     * @param originalStyle
     */
    void save(OriginalStyle originalStyle);

    /**
     * 独自スタイルの更新
     * @param originalStyle
     */
    void update(OriginalStyle originalStyle);

    /**
     * 独自スタイルの削除
     */
    void delete();

    /**
     * 存在確認
     * @return
     */
    boolean exist();

    /**
     * 独自スタイルの照会
     * @return
     */
    OriginalStyle get();
}
