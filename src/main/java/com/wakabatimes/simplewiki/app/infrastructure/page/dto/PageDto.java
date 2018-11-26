package com.wakabatimes.simplewiki.app.infrastructure.page.dto;

import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import lombok.Data;

@Data
public class PageDto {
    String id;
    String name;
    Integer type;
    Integer sortNumber;

    public PageDto(){

    }

    public PageDto(Page page) {
        this.id = page.getPageId().getValue();
        this.name = page.getPageName().getValue();
        this.type = page.getPageType().getId();
    }
}
