package com.wakabatimes.simplewiki.app.domain.service.system_initialize;

import com.wakabatimes.simplewiki.app.domain.aggregates.sytem_initialize.SystemInitialize;

public interface SystemInitializeService {
    /**
     * システム初期設定
     * @param systemInitialize
     */
    void save(SystemInitialize systemInitialize);
}
