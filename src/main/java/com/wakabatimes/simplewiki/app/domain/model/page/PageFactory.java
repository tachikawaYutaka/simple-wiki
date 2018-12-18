package com.wakabatimes.simplewiki.app.domain.model.page;

import java.util.UUID;

public class PageFactory {
    public static Page create(PageName pageName,PageType pageType){
        PageId pageId = new PageId(UUID.randomUUID().toString());
        return new Page(pageId, pageName,pageType);
    }
}
