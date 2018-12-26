package com.wakabatimes.simplewiki.app.interfaces.system.dto;

import com.wakabatimes.simplewiki.app.domain.model.system.System;
import lombok.Data;

@Data
public class SystemResponseDto {
    private String id;
    private String name;

    public SystemResponseDto(System system) {
        this.id = system.getSystemId().getValue();
        this.name = system.getSystemName().getValue();
    }
}
