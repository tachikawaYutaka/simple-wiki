package com.wakabatimes.simplewiki.app.domain.model.system;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
/**
 * root entity
 */
@Slf4j
@EqualsAndHashCode
public class System {
    @Getter
    @NonNull
    SystemId systemId;

    @Getter
    @NonNull
    SystemName systemName;

    public System(SystemId systemId, SystemName systemName) {
        this.systemId = systemId;
        this.systemName = systemName;
    }
}
