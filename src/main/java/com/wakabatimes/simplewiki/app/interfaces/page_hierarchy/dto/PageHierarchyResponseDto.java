package com.wakabatimes.simplewiki.app.interfaces.page_hierarchy.dto;

import com.wakabatimes.simplewiki.app.domain.aggregates.page_hierarchy.PageHierarchy;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import lombok.Data;

import java.util.List;

@Data
public class PageHierarchyResponseDto {
    private String id;
    private String name;
    private String path;
    private Integer sort;
    private List<PageHierarchyResponseDto> pages;

    public PageHierarchyResponseDto(PageHierarchy pageHierarchy){
        this.id = pageHierarchy.getPage().getPageId().getValue();
        this.name = pageHierarchy.getPage().getPageName().getValue();
        this.path = pageHierarchy.getPagePath().getValue();
        this.sort = pageHierarchy.getPage().getPageSortNumber().getValue();
    }
}
