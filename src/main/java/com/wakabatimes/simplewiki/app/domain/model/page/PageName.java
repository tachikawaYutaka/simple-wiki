package com.wakabatimes.simplewiki.app.domain.model.page;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * value object
 */
@Slf4j
@Value
public class PageName {
    String value;

    public PageName(String value) {
        validateUserName(value);
        this.value = value;
    }

    private void validateUserName(String value) {
        Integer count = value.length();
        if(count < 3 || count > 255) {
            throw new RuntimeException("ページ名が正しくありません。3～255文字で入力してください。");
        }

        String regex = "(<|>|&|!|\\?|\\/|\\\\|\\@|\\%|\\.|\\,|\\$|\\#|\\(|\\)|\\`|\\:|\\;|\\{|\\}|\\*|\\+|\\^|\\=|\\~|\\||\\[|\\]|\"|\'|\\u005c|\\u0020)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);
        if (m.find()){
            throw new RuntimeException("ページ名が正しくありません。ハイフンとアンダーバー以外の半角記号は使用できません。");
        }
    }
}
