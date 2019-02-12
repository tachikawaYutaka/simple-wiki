package com.wakabatimes.simplewiki.app.interfaces.menu.form;

import lombok.Data;

@Data
public class MenuUpdateForm {
    private String menuName;
    private Integer menuViewLimit;
}
