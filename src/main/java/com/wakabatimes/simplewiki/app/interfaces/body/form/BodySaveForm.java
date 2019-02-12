package com.wakabatimes.simplewiki.app.interfaces.body.form;

import lombok.Data;

@Data
public class BodySaveForm {
    private String pageName;
    private String content;
    private String html;
}
