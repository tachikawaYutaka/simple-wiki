package com.wakabatimes.simplewiki.view.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserAdminController {
    @GetMapping("/system/users")
    public String system_user_admin(){
        return "system/system_user";
    }
}
