package com.wakabatimes.simplewiki.app.domain.model.body;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

/**
 * value object
 */
@Slf4j
@Value
public class BodyContent {
    String value;

    public BodyContent(String value) {
        validateBodyContent(value);
        this.value = value;
    }

    private void validateBodyContent(String value) {
        Integer count = value.length();
        if(count < 0 || count > 100000) {
            throw new RuntimeException("BodyContent is incorrect. 0 or more characters 100000 characters within");
        }
    }
}
