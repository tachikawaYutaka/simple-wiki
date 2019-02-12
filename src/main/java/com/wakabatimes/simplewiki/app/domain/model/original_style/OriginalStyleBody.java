package com.wakabatimes.simplewiki.app.domain.model.original_style;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

/**
 * value object
 */
@Slf4j
@Value
public class OriginalStyleBody {
    String value;

    public OriginalStyleBody(String value) {
        validateBodyContent(value);
        this.value = value;
    }

    private void validateBodyContent(String value) {
        Integer count = value.length();
        if(count < 1 || count > 100000) {
            throw new RuntimeException("スタイルの入力が正しくありません。1～100000字内で入力してください。");
        }
    }
}
