package com.wakabatimes.simplewiki.view.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SystemController {
    @GetMapping("/system/name")
    public String system_name(){
        return "system/system_name";
    }
}
