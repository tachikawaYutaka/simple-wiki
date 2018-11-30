package com.wakabatimes.simplewiki.view.public_content;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/public_content/page")
    public String public_content_page(){
        return "public_content/page";
    }
}
