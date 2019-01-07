package com.wakabatimes.simplewiki.app.interfaces.user.form;

import lombok.Data;

@Data
public class UserSaveForm {
    private String userName;
    private String userPassword;
    private Integer userRole;
}
