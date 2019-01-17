package com.wakabatimes.simplewiki.app.infrastructure.system.dto;

import com.wakabatimes.simplewiki.app.domain.model.system.System;
import lombok.Data;

@Data
public class SystemDto {
    private String id;
    private String name;

    public SystemDto(){

    }

    public SystemDto(System system) {
        this.id = system.getSystemId().getValue();
        this.name = system.getSystemName().getValue();
    }
}
