package com.wakabatimes.simplewiki.app.domain.model.search;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * value object
 */
@Slf4j
@Value
public class SearchInput {
    String value;

    public SearchInput(String value) {
        validateSearchInput(value);
        this.value = value;
    }

    private void validateSearchInput(String value) {
        Integer count = value.length();
        if(count < 1 || count > 255) {
            throw new RuntimeException("入力が正しくありません。1～255文字で入力してください。");
        }

        String regex = "(<|>|&|!|\\?|\\/|\\\\|\\@|\\%|\\.|\\,|\\$|\\#|\\(|\\)|\\`|\\:|\\;|\\{|\\}|\\*|\\+|\\^|\\=|\\~|\\||\\[|\\]|\"|\'|\\u005c|\\u0020)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);
        if (m.find()){
            throw new RuntimeException("入力が正しくありません。ハイフンとアンダーバー以外の半角記号は使用できません。");
        }
    }
}
