package com.wakabatimes.simplewiki.view.private_content;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/private_content/page")
    public String private_content_page(){
        return "private_content/page";
    }
}
