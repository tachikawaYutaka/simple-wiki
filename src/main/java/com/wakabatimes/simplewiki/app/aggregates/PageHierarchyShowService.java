package com.wakabatimes.simplewiki.app.aggregates;

import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageId;
import com.wakabatimes.simplewiki.app.domain.model.page.Pages;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import com.wakabatimes.simplewiki.app.interfaces.page_hierarchy.dto.PageHierarchyResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PageHierarchyShowService {
    @Autowired
    private PageService pageService;

    public List<PageHierarchyResponseDto> list(MenuId menuId) {
        List<PageHierarchyResponseDto> result = new ArrayList<>();
        Pages pages = pageService.listRoot(menuId);
        for(Page page : pages.list()){
            String path = "";
            PageHierarchyResponseDto pageHierarchyResponseDto = new PageHierarchyResponseDto(page);
            path += pageHierarchyResponseDto.getName() + "/";
            pageHierarchyResponseDto.setPath(path);
            List<PageHierarchyResponseDto> children = getChildren(page.getPageId(), path);
            pageHierarchyResponseDto.setPages(children);
            result.add(pageHierarchyResponseDto);
        }
        return result;
    }

    private List<PageHierarchyResponseDto> getChildren(PageId pageId, String path) {
        List<PageHierarchyResponseDto> result = new ArrayList<>();
        Pages pages = pageService.listBranch(pageId);
        for(Page page : pages.list()){
            String childPath = path;
            PageHierarchyResponseDto pageHierarchyResponseDto = new PageHierarchyResponseDto(page);
            childPath += pageHierarchyResponseDto.getName() + "/";
            pageHierarchyResponseDto.setPath(childPath);
            List<PageHierarchyResponseDto> children = getChildren(page.getPageId(), childPath);
            pageHierarchyResponseDto.setPages(children);
            result.add(pageHierarchyResponseDto);
        }
        return result;
    }
}
