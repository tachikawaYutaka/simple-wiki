package com.wakabatimes.simplewiki.app.domain.model.page;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum PageType {
    ROOT(0),
    BRANCH(1);

    @Getter
    private Integer id;

    private PageType(Integer id) {
        this.id = id;
    }

    public static PageType getById(Integer id) {
        for(PageType pageType : PageType.values()){
            if(pageType.id == id){
                return pageType;
            }
        }
        return PageType.BRANCH;
    }
}
