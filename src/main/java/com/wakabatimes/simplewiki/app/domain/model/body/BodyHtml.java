package com.wakabatimes.simplewiki.app.domain.model.body;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

/**
 * value object
 */
@Slf4j
@Value
public class BodyHtml {
    String value;

    public BodyHtml(String value) {
        validateBodyHtml(value);
        this.value = value;
    }

    private void validateBodyHtml(String value) {
        Integer count = value.length();
        if(count <= 0 || count > 10000) {
            throw new RuntimeException("BodyContent is incorrect. 0 or more characters 10000 characters within");
        }
    }
}
