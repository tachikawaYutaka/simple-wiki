package com.wakabatimes.simplewiki.view.private_content;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {
    @GetMapping("/private_content/menu")
    public String private_content_menu(){
        return "private_content/menu";
    }
}
