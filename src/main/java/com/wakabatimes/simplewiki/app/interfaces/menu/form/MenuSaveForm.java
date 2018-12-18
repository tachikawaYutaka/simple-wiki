package com.wakabatimes.simplewiki.app.interfaces.menu.form;

import lombok.Data;

@Data
public class MenuSaveForm {
    String name;
    String viewLimit;
    String[] pathList;
}
