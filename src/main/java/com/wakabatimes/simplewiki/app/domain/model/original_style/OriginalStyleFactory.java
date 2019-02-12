package com.wakabatimes.simplewiki.app.domain.model.original_style;

import java.util.UUID;

public class OriginalStyleFactory {
    public static OriginalStyle create(OriginalStyleBody originalStyleBody){
        OriginalStyleId originalStyleId = new OriginalStyleId(UUID.randomUUID().toString());
        return new OriginalStyle(originalStyleId, originalStyleBody);
    }
}
