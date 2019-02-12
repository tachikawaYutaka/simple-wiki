package com.wakabatimes.simplewiki.app.domain.model.original_html;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

/**
 * value object
 */
@Slf4j
@Value
public class OriginalHtmlId {
    String value;

    public OriginalHtmlId (String value) {
        validateUUID(value);
        this.value = value;
    }

    private void validateUUID(String value) {
        String inputPattern = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";

        if (!Pattern.matches(inputPattern, value)) {
            throw new RuntimeException("Input is incorrect. Expected value [" + inputPattern + "] [value=" + value + "]");
        }
    }
}
