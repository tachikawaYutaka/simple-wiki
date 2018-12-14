package com.wakabatimes.simplewiki.app.interfaces.menu.dto;

import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import lombok.Data;

@Data
public class MenuResponseDto {
    private String id;
    private String name;
    private String limit;

    public MenuResponseDto(Menu menu) {
        this.id = menu.getMenuId().getValue();
        this.name = menu.getMenuName().getValue();
        this.limit = menu.getMenuLimit().name();
    }
}
