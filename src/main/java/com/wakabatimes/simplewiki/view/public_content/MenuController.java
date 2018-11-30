package com.wakabatimes.simplewiki.view.public_content;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {
    @GetMapping("/public_content/menu")
    public String public_content_menu(){
        return "public_content/menu";
    }
}
