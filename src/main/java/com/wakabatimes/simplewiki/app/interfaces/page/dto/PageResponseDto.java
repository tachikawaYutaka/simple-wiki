package com.wakabatimes.simplewiki.app.interfaces.page.dto;

import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import lombok.Data;

@Data
public class PageResponseDto {
    private String id;
    private String name;
    private String type;
    private Integer sort;

    public PageResponseDto(Page page) {
        this.id = page.getPageId().getValue();
        this.name = page.getPageName().getValue();
        this.type = page.getPageType().name();
        this.sort = page.getPageSortNumber().getValue();
    }
}
