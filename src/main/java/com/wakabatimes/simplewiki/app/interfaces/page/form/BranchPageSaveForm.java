package com.wakabatimes.simplewiki.app.interfaces.page.form;

import lombok.Data;

@Data
public class BranchPageSaveForm{
    private String menuId;
    private String parentId;
    private String name;
}
