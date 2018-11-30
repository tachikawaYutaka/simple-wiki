package com.wakabatimes.simplewiki.view.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/user/name")
    public String user_name(){
        return "user/user_name";
    }

    @GetMapping("/user/password")
    public String user_password(){
        return "user/user_password";
    }
}
