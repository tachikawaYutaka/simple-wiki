package com.wakabatimes.simplewiki.app.interfaces.page.form;

import lombok.Data;

@Data
public class BranchPageSaveForm{
    String menuId;
    String parentId;
    String name;
}
