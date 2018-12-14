package com.wakabatimes.simplewiki.app.interfaces.user.form;

import lombok.Data;

@Data
public class UserInitializeForm {
    private String systemName;
    private String userName;
    private String userPassword;
}
