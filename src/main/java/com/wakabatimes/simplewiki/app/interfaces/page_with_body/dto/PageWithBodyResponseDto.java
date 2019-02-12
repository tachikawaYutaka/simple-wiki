package com.wakabatimes.simplewiki.app.interfaces.page_with_body.dto;

import com.wakabatimes.simplewiki.app.domain.aggregates.page_with_body.PageWithBody;
import lombok.Data;

@Data
public class PageWithBodyResponseDto {
    private String pageId;
    private String pageName;
    private String bodyHtml;

    public PageWithBodyResponseDto(PageWithBody pageWithBody){
        this.pageId = pageWithBody.getPage().getPageId().getValue();
        this.pageName = pageWithBody.getPage().getPageName().getValue();
        this.bodyHtml = pageWithBody.getBody().getBodyHtml().getValue();
    }
}
