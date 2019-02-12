package com.wakabatimes.simplewiki.app.domain.model.system;

import java.util.UUID;

public class SystemFactory {
    public static System create(SystemName systemName){
        SystemId systemId = new SystemId(UUID.randomUUID().toString());
        return new System(systemId, systemName);
    }
}
