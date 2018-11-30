package com.wakabatimes.simplewiki.app.domain.model.original_html;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

/**
 * value object
 */
@Slf4j
@Value
public class OriginalHtmlBody {
    String value;

    public OriginalHtmlBody(String value) {
        validateBodyContent(value);
        this.value = value;
    }

    private void validateBodyContent(String value) {
        Integer count = value.length();
        if(count < 1 || count > 10000) {
            throw new RuntimeException("BodyContent is incorrect. 1 or more characters 10000 characters within");
        }
    }
}
