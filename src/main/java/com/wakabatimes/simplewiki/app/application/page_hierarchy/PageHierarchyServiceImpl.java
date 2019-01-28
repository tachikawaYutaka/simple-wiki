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
        List<PageHierarchy> result = new ArrayList<>();
        Pages pages = pageService.listRoot(menuId);
        for(Page page : pages.list()){
            String path = "";
            path += page.getPageName().getValue() + "/";

            PagePath pagePath = new PagePath(path);
            List<PageHierarchy> children = getChildren(page.getPageId(), path);

            PageHierarchy pageHierarchy = new PageHierarchy(page,pagePath,new PageHierarchies(children));
            result.add(pageHierarchy);
        }
        PageHierarchies results = new PageHierarchies(result);
        return results;
    }

    private List<PageHierarchy> getChildren(PageId pageId, String path) {
        List<PageHierarchy> result = new ArrayList<>();
        Pages pages = pageService.listBranch(pageId);
        for(Page page : pages.list()){
            String childPath = path;
            childPath += page.getPageName().getValue() + "/";

            PagePath pagePath = new PagePath(childPath);
            List<PageHierarchy> children = getChildren(page.getPageId(), childPath);
            PageHierarchy pageHierarchy = new PageHierarchy(page,pagePath,new PageHierarchies(children));
            result.add(pageHierarchy);
        }
        return result;
    }

    @Override
    public PageHierarchies getCurrentPath(PageId pageId) {
        List<PageHierarchy> result = new ArrayList<>();

        Page page = pageService.get(pageId);
        String path =  "/" + page.getPageName().getValue() ;
        PagePath pagePath = new PagePath(path);
        PageHierarchy pageHierarchy = new PageHierarchy(page,pagePath,new PageHierarchies());
        result.add(pageHierarchy);

        List<PageHierarchy> parent = new ArrayList<>();
        if(page.getPageType().getId() == PageType.BRANCH.getId()){
            parent = getParents(path,page.getPageId());
        }
        List<PageHierarchy> results = Stream.concat(result.stream(), parent.stream()).collect(Collectors.toList());
        Collections.reverse(results);

        PageHierarchies pageHierarchies = new PageHierarchies();
        String mainPath = results.get(0).getPagePath().getValue();
        String[] split = mainPath.split("/");

        int i = 1;
        for(PageHierarchy resultPage : results) {
            String modPath = "";
            int j = 0;
            while (j < i + 1){
                modPath += split[j] + "/";
                j++;
            }
            PagePath pagePath2 = new PagePath(modPath);
            PageHierarchy pageHierarchy2 = new PageHierarchy(resultPage.getPage(),pagePath2,resultPage.getPageHierarchies());
            pageHierarchies.add(pageHierarchy2);
            i++;
        }
        return pageHierarchies;
    }

    private List<PageHierarchy> getParents(String path, PageId pageId) {
        List<PageHierarchy> result = new ArrayList<>();
        Page page = pageService.getParent(pageId);

        String currentPath = "/" + page.getPageName().getValue() + path;
        PagePath pagePath = new PagePath(currentPath);
        PageHierarchy pageHierarchy = new PageHierarchy(page,pagePath,new PageHierarchies());
        result.add(pageHierarchy);

        List<PageHierarchy> parent = new ArrayList<>();
        if(page.getPageType().getId() == PageType.BRANCH.getId()){
            parent = getParents(currentPath,page.getPageId());
        }

        List<PageHierarchy> results = Stream.concat(result.stream(), parent.stream()).collect(Collectors.toList());
        return results;
    }
}
