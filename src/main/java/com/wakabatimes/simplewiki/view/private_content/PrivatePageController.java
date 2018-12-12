package com.wakabatimes.simplewiki.view.private_content;

import net.sourceforge.plantuml.ant.PlantUmlTask;
import net.sourceforge.plantuml.code.Transcoder;
import net.sourceforge.plantuml.code.TranscoderUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.util.Iterator;
import java.util.Scanner;

@Controller
public class PrivatePageController {
    @GetMapping("/private_content/{menuName}/{pagePath}")
    public String private_content_page(@PathVariable String menuName, @PathVariable String pagePath){
        return "private_content/page";
    }

}
