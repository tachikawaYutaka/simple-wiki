package com.wakabatimes.simplewiki.app.aggregates;

import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageId;
import com.wakabatimes.simplewiki.app.domain.model.page.PageType;
import com.wakabatimes.simplewiki.app.domain.model.page.Pages;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import com.wakabatimes.simplewiki.app.interfaces.page_hierarchy.dto.PageHierarchyResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public List<PageHierarchyResponseDto> getCurrentPath(PageId pageId) {
        List<PageHierarchyResponseDto> result = new ArrayList<>();
        Page page = pageService.get(pageId);
        PageHierarchyResponseDto pageHierarchyResponseDto = new PageHierarchyResponseDto(page);
        String path =  "/" + page.getPageName().getValue() ;
        pageHierarchyResponseDto.setPath(path);
        result.add(pageHierarchyResponseDto);
        List<PageHierarchyResponseDto> parent = new ArrayList<>();
        if(page.getPageType().getId() == PageType.BRANCH.getId()){
            parent = getParents(path,page.getPageId());
        }
        List<PageHierarchyResponseDto> results = Stream.concat(result.stream(), parent.stream()).collect(Collectors.toList());
        Collections.reverse(results);

        String mainPath = results.get(0).getPath();
        String[] split = mainPath.split("/");

        int i = 1;
        for(PageHierarchyResponseDto resultPage : results) {
            String modPath = "";
            int j = 0;
            while (j < i + 1){
                modPath += split[j] + "/";
                j++;
            }
            resultPage.setPath(modPath);
            i++;
        }
        return results;
    }

    private List<PageHierarchyResponseDto> getParents(String path, PageId pageId) {
        List<PageHierarchyResponseDto> result = new ArrayList<>();
        Page page = pageService.getParent(pageId);
        PageHierarchyResponseDto pageHierarchyResponseDto = new PageHierarchyResponseDto(page);
        String currentPath = "/" + page.getPageName().getValue() + path;
        pageHierarchyResponseDto.setPath(currentPath);
        result.add(pageHierarchyResponseDto);
        List<PageHierarchyResponseDto> parent = new ArrayList<>();
        if(page.getPageType().getId() == PageType.BRANCH.getId()){
            parent = getParents(currentPath,page.getPageId());
        }
        List<PageHierarchyResponseDto> results = Stream.concat(result.stream(), parent.stream()).collect(Collectors.toList());
        return results;
    }
}
