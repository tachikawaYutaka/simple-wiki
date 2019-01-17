package com.wakabatimes.simplewiki.app.domain.aggregates.page_with_body;

import com.wakabatimes.simplewiki.app.interfaces.page_with_body.dto.PageWithBodyResponseDto;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PageWithBodies {
    List<PageWithBody> pageWithBodies;

    public PageWithBodies(){
        pageWithBodies = new ArrayList<>();
    }

    public PageWithBodies(List<PageWithBody> pageHierarchies) {
        this.pageWithBodies = new ArrayList<>(pageHierarchies);
    }

    /**
     * PageWithBodies add
     * @throws RuntimeException
     */
    public void add(@NonNull PageWithBody pageWithBody) {
        this.pageWithBodies.add(pageWithBody);
    }

    /**
     * PageWithBody count
     * @return Integer
     */
    public Integer size() {
        return this.pageWithBodies.size();
    }


    /**
     * get list
     *
     * @return List<PageWithBody>
     */
    public List<PageWithBody> list() {
        return Collections.unmodifiableList(this.pageWithBodies);
    }

    public List<PageWithBodyResponseDto> responseList() {
        List<PageWithBodyResponseDto> result = new ArrayList<>();
        List<PageWithBody> pageWithBodies = Collections.unmodifiableList(this.pageWithBodies);
        for(PageWithBody pageWithBody : pageWithBodies) {
            PageWithBodyResponseDto pageWithBodyResponseDto = new PageWithBodyResponseDto(pageWithBody);
            result.add(pageWithBodyResponseDto);
        }
        return result;
    }
}
