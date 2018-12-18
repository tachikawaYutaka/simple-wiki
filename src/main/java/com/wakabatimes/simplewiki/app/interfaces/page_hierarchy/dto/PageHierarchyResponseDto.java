package com.wakabatimes.simplewiki.app.interfaces.page_hierarchy.dto;

import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import lombok.Data;

import java.util.List;

@Data
public class PageHierarchyResponseDto {
    String id;
    String name;
    String path;
    List<PageHierarchyResponseDto> pages;

    public PageHierarchyResponseDto(Page page){
        this.id = page.getPageId().getValue();
        this.name = page.getPageName().getValue();
    }
}
