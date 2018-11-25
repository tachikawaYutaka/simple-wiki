package com.wakabatimes.simplewiki.app.domain.model.page;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * root entity
 */
@Slf4j
@EqualsAndHashCode
public class Page {
    @Getter
    @NonNull
    PageId pageId;

    @Getter
    @NonNull
    PageName pageName;

    @Getter
    @NonNull
    PageType pageType;

    @Getter
    Pages pages;

    public Page(PageId pageId, PageName pageName,PageType pageType, Pages pages) {
        this.pageId = pageId;
        this.pageName = pageName;
        this.pageType = pageType;
        this.pages = pages;
    }

    public boolean isRoot(Page page) {
        if(page.getPageType().equals(PageType.ROOT)) {
            return true;
        }
        return false;
    }
}
