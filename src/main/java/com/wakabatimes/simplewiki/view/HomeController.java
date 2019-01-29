package com.wakabatimes.simplewiki.view;

import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.user.*;
import com.wakabatimes.simplewiki.app.domain.service.menu.MenuService;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import com.wakabatimes.simplewiki.app.domain.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Principal;

@Controller
@Slf4j
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private PageService pageService;

    @GetMapping("/")
    public String home(RedirectAttributes attr) throws UnsupportedEncodingException {
        Users users = userService.list();
        if(users.size() == 0){
            return "start/start";
        }

        Menu menu = menuService.getHomeMenu();
        try{
        Page page = pageService.getHomePage(menu.getMenuId());
        String url = "/contents/" + menu.getMenuLimit().name().toLowerCase() + "/" + URLEncoder.encode(menu.getMenuName().getValue(),"UTF-8") + "/" + URLEncoder.encode(page.getPageName().getValue(),"UTF-8");

        return "redirect:" + url;
        } catch (RuntimeException e){
            log.error("Error:", e);
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e.getMessage());
            String url = "/contents/" + menu.getMenuLimit().name().toLowerCase() + "/" + URLEncoder.encode(menu.getMenuName().getValue(),"UTF-8");
            return "redirect:" + url;
        }
    }

    @ResponseBody
    @GetMapping(path="/puml_image", produces = MediaType.IMAGE_PNG_VALUE)
    public HttpEntity<byte[]> puml_image(@RequestParam("url")  String url) throws IOException {
        String decode  = URLDecoder.decode(url, "UTF-8");
        SourceStringReader reader = new SourceStringReader(decode.replace(';','\n'));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        reader.generateImage(bos, new FileFormatOption(FileFormat.PNG, false));

        byte[] b = bos.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(b.length);
        return new HttpEntity<byte[]>(b, headers);
    }
}
