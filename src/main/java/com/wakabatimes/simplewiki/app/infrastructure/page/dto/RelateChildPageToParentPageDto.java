package com.wakabatimes.simplewiki.app.infrastructure.page.dto;

import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageId;
import lombok.Data;

@Data
public class RelateChildPageToParentPageDto {
    String childPageId;
    String parentPageId;

    public RelateChildPageToParentPageDto(Page childPage, PageId parentId) {
        this.childPageId = childPage.getPageId().getValue();
        this.parentPageId = parentId.getValue();
    }
}
