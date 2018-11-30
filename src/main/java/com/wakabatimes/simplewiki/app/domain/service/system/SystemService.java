package com.wakabatimes.simplewiki.app.domain.service.system;

import com.wakabatimes.simplewiki.app.domain.model.system.System;

public interface SystemService {
    /**
     * システム設定の保存
     * @param system
     */
    void save(System system);

    /**
     * システム設定の更新
     * @param system
     */
    void update(System system);

    /**
     * 存在確認
     * @return
     */
    boolean exist();

    /**
     * システム設定の照会
     * @return
     */
    System get();
}
