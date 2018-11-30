package com.wakabatimes.simplewiki.app.domain.model.original_style;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * root entity
 */
@Slf4j
@EqualsAndHashCode
public class OriginalStyle {
    @NonNull
    @Getter
    OriginalStyleId originalStyleId;

    @NonNull
    @Getter
    OriginalStyleBody originalStyleBody;

    public OriginalStyle(OriginalStyleId originalStyleId, OriginalStyleBody originalStyleBody) {
        this.originalStyleId = originalStyleId;
        this.originalStyleBody = originalStyleBody;
    }
}
