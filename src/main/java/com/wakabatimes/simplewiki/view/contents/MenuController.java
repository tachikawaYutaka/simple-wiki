package com.wakabatimes.simplewiki.view.contents;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MenuController {
    @GetMapping("/contents/{menuName}")
    public String public_content_menu(@PathVariable String menuName){
        return "contents/menu";
    }
}
