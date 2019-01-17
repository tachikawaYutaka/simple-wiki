package com.wakabatimes.simplewiki.app.application.page_hierarchy;

import com.wakabatimes.simplewiki.app.domain.aggregates.page_hierarchy.PageHierarchies;
import com.wakabatimes.simplewiki.app.domain.aggregates.page_hierarchy.PageHierarchy;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.page.*;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import com.wakabatimes.simplewiki.app.domain.service.page_hierarchy.PageHierarchyService;
import com.wakabatimes.simplewiki.app.interfaces.page_hierarchy.dto.PageHierarchyResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PageHierarchyServiceImpl implements PageHierarchyService{
    @Autowired
    private PageService pageService;

    @Override
    public PageHierarchies list(MenuId menuId) {
        PageHierarchies result = new PageHierarchies();
        Pages pages = pageService.listRoot(menuId);
        for(Page page : pages.list()){
            String path = "";
            path += page.getPageName().getValue() + "/";

            PagePath pagePath = new PagePath(path);
            PageHierarchies children = getChildren(page.getPageId(), path);

            PageHierarchy pageHierarchy = new PageHierarchy(page,pagePath,children);
            result.add(pageHierarchy);
        }
        return result;
    }

    private PageHierarchies getChildren(PageId pageId, String path) {
        PageHierarchies result = new PageHierarchies();
        Pages pages = pageService.listBranch(pageId);
        for(Page page : pages.list()){
            String childPath = path;
            childPath += page.getPageName().getValue() + "/";

            PagePath pagePath = new PagePath(childPath);
            PageHierarchies children = getChildren(page.getPageId(), childPath);
            PageHierarchy pageHierarchy = new PageHierarchy(page,pagePath,children);
            result.add(pageHierarchy);
        }
        return result;
    }

    @Override
    public PageHierarchies getCurrentPath(PageId pageId) {
        PageHierarchies result = new PageHierarchies();

        Page page = pageService.get(pageId);
        String path =  "/" + page.getPageName().getValue() ;

        PageHierarchies parent = new PageHierarchies();

        if(page.getPageType().getId() == PageType.BRANCH.getId()){
            parent = getParents(path,page.getPageId());
        }
        List<PageHierarchy> results = Stream.concat(result.list().stream(), parent.list().stream()).collect(Collectors.toList());
        Collections.reverse(results);

        String mainPath = results.get(0).getPagePath().getValue();
        String[] split = mainPath.split("/");

        int i = 1;
        PageHierarchies pageHierarchies = new PageHierarchies();
        for(PageHierarchy resultPage : results) {
            String modPath = "";
            int j = 0;
            while (j < i + 1){
                modPath += split[j] + "/";
                j++;
            }
            PagePath pagePath = new PagePath(modPath);
            PageHierarchy pageHierarchy = new PageHierarchy(resultPage.getPage(),pagePath,resultPage.getPageHierarchies());
            pageHierarchies.add(pageHierarchy);
            i++;
        }
        return pageHierarchies;
    }

    private PageHierarchies getParents(String path, PageId pageId) {
        PageHierarchies result = new PageHierarchies();
        Page page = pageService.getParent(pageId);

        String currentPath = "/" + page.getPageName().getValue() + path;
        PagePath pagePath = new PagePath(currentPath);

        PageHierarchies parent = new PageHierarchies();
        if(page.getPageType().getId() == PageType.BRANCH.getId()){
            parent = getParents(currentPath,page.getPageId());
        }
        PageHierarchy pageHierarchy = new PageHierarchy(page,pagePath,parent);
        result.add(pageHierarchy);

        List<PageHierarchy> lists = Stream.concat(result.list().stream(), parent.list().stream()).collect(Collectors.toList());
        PageHierarchies results = new PageHierarchies(lists);
        return results;
    }
}
