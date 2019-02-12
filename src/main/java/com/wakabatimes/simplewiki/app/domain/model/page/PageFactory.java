package com.wakabatimes.simplewiki.app.domain.model.page;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class PageFactory {
    public static Page create(PageName pageName,PageType pageType){
        PageId pageId = new PageId(UUID.randomUUID().toString());
        return new Page(pageId, pageName,pageType);
    }

    public static Page createWithSortNumber(PageName pageName, PageType pageType, PageSortNumber pageSortNumber){
        PageId pageId = new PageId(UUID.randomUUID().toString());
        return new Page(pageId, pageName,pageType,pageSortNumber);
    }

    public static Page createNewPage(PageType pageType) {
        PageId pageId = new PageId(UUID.randomUUID().toString());
        LocalDateTime nowDate = LocalDateTime.now();
        DateTimeFormatter df1 =
                DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String s = df1.format(nowDate);
        PageName pageName = new PageName("新しいページ" + s);
        return new Page(pageId, pageName,pageType);
    }
}
