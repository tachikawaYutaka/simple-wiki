package com.wakabatimes.simplewiki.view.public_content;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PublicPageController {
    @GetMapping("/public_content/{menuName}/{pagePath}")
    public String public_content_page(@PathVariable String menuName, @PathVariable String pagePath){
        return "public_content/page";
    }
}
