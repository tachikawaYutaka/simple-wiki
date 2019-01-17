package com.wakabatimes.simplewiki.app.interfaces.page_hierarchy.dto;

import com.wakabatimes.simplewiki.app.domain.aggregates.page_hierarchy.PageHierarchy;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import lombok.Data;

import java.util.List;

@Data
public class PageHierarchyResponseDto {
    String id;
    String name;
    String path;
    List<PageHierarchyResponseDto> pages;

    public PageHierarchyResponseDto(PageHierarchy pageHierarchy){
        this.id = pageHierarchy.getPage().getPageId().getValue();
        this.name = pageHierarchy.getPage().getPageName().getValue();
    }
}
