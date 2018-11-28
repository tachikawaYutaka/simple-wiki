package com.wakabatimes.simplewiki.app.infrastructure.body.dto;

import com.wakabatimes.simplewiki.app.domain.model.body.BodyId;
import com.wakabatimes.simplewiki.app.domain.model.page.PageId;
import lombok.Data;

@Data
public class RelateBodyToPageDto {
    String bodyId;
    String pageId;

    public RelateBodyToPageDto(BodyId bodyId, PageId pageId) {
        this.bodyId = bodyId.getValue();
        this.pageId = pageId.getValue();
    }
}
