package com.wakabatimes.simplewiki.view.private_content;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PrivateMenuController {
    @GetMapping("/private_content/{menuName}")
    public String private_content_menu(@PathVariable String menuName){
        return "private_content/menu";
    }
}
