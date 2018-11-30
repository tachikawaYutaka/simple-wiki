package com.wakabatimes.simplewiki.view.public_content;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PublicMenuController {
    @GetMapping("/public_content/{menuName}")
    public String public_content_menu(@PathVariable String menuName){
        return "public_content/menu";
    }
}
