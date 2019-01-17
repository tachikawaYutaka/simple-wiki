package com.wakabatimes.simplewiki.app.domain.model.page;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

/**
 * value object
 */
@Slf4j
@Value
public class PagePath {
    String value;

    public PagePath(String value) {
        validatePagePath(value);
        this.value = value;
    }

    private void validatePagePath(String value) {
        Integer count = value.length();
        if(count < 1 || count > 2083) {
            throw new RuntimeException("ページパスが正しくありません。");
        }
    }
}
