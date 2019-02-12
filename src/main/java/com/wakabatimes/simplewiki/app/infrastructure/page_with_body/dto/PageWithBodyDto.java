package com.wakabatimes.simplewiki.app.infrastructure.page_with_body.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PageWithBodyDto {
    private String pageId;
    private String pageName;
    private Integer pageType;
    private Integer pageSortNumber;
    private String bodyId;
    private String bodyContent;
    private String bodyHtml;
    private Integer bodyType;
    private Date bodyCreatedDate;

    public PageWithBodyDto(){

    }
}
