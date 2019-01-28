package com.wakabatimes.simplewiki.app.infrastructure.root_page.dto;

import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import lombok.Data;

@Data
public class RootPageDto {
    private String menuId;
    private String menuName;
    private Integer menuViewLimit;
    private Integer menuSortNumber;
    private String pageId;
    private String pageName;
    private Integer pageType;
    private Integer pageSortNumber;

    public RootPageDto() {

    }
}
