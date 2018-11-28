package com.wakabatimes.simplewiki.app.infrastructure.page.dto;

import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import lombok.Data;

@Data
public class RelatePageToMenuDto {
    String pageId;
    String menuId;

    public RelatePageToMenuDto(Page page, MenuId menuId) {
        this.pageId = page.getPageId().getValue();
        this.menuId = menuId.getValue();
    }
}
