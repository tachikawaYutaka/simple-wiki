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
        if(count <= 0 || count > 1000000) {
            throw new RuntimeException("内容にエラーがあります。0～1000000内で入力してください。");
        }
    }
}
