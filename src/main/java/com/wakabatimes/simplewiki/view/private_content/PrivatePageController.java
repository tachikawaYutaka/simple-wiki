package com.wakabatimes.simplewiki.view.private_content;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PrivatePageController {
    @GetMapping("/private_content/{menuName}/{pagePath}")
    public String private_content_page(@PathVariable String menuName, @PathVariable String pagePath){
        return "private_content/page";
    }
}
