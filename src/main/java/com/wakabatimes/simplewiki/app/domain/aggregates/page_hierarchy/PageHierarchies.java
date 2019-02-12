package com.wakabatimes.simplewiki.app.domain.aggregates.page_hierarchy;

import com.wakabatimes.simplewiki.app.interfaces.page_hierarchy.dto.PageHierarchyResponseDto;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PageHierarchies {
    List<PageHierarchy> pageHierarchies;

    public PageHierarchies(){
        pageHierarchies = new ArrayList<>();
    }

    public PageHierarchies(List<PageHierarchy> pageHierarchies) {
        this.pageHierarchies = new ArrayList<>(pageHierarchies);
    }

    /**
     * PageHierarchies add
     * @throws RuntimeException
     */
    public void add(@NonNull PageHierarchy pageHierarchy) {
        this.pageHierarchies.add(pageHierarchy);
    }

    /**
     * PageHierarchy count
     * @return Integer
     */
    public Integer size() {
        return this.pageHierarchies.size();
    }


    /**
     * get list
     *
     * @return List<PageHierarchy>
     */
    public List<PageHierarchy> list() {
        return Collections.unmodifiableList(this.pageHierarchies);
    }

    /**
     * get response list
     * @return
     */
    public List<PageHierarchyResponseDto> responseList(){
        List<PageHierarchyResponseDto> result = new ArrayList<>();
        List<PageHierarchy> hierarchies = Collections.unmodifiableList(this.pageHierarchies);
        for(PageHierarchy pageHierarchy : hierarchies) {
            PageHierarchyResponseDto pageHierarchyResponseDto = new PageHierarchyResponseDto(pageHierarchy);
            List<PageHierarchyResponseDto> children = new ArrayList<>();
            children = getChildren(pageHierarchy.getPageHierarchies().list(),children);
            pageHierarchyResponseDto.setPages(children);
            result.add(pageHierarchyResponseDto);
        }
        return result;
    }

    private List<PageHierarchyResponseDto> getChildren(List<PageHierarchy> list, List<PageHierarchyResponseDto> responseList) {
        for(PageHierarchy pageHierarchy : list) {
            PageHierarchyResponseDto pageHierarchyResponseDto = new PageHierarchyResponseDto(pageHierarchy);
            List<PageHierarchyResponseDto> children = new ArrayList<>();
            children = getChildren(pageHierarchy.getPageHierarchies().list(),children);
            pageHierarchyResponseDto.setPages(children);
            responseList.add(pageHierarchyResponseDto);
        }
        return responseList;
    }

}
