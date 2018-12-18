package com.wakabatimes.simplewiki.app.interfaces.page.dto;

import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import lombok.Data;

@Data
public class PageResponseDto {
    String id;
    String name;
    String type;

    public PageResponseDto(Page page) {
        this.id = page.getPageId().getValue();
        this.name = page.getPageName().getValue();
        this.type = page.getPageType().name();
    }
}
