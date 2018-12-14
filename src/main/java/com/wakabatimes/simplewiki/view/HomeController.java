package com.wakabatimes.simplewiki.view;

import com.wakabatimes.simplewiki.app.aggregates.SystemInitializeService;
import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuFactory;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuLimit;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuName;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageFactory;
import com.wakabatimes.simplewiki.app.domain.model.page.PageName;
import com.wakabatimes.simplewiki.app.domain.model.page.PageType;
import com.wakabatimes.simplewiki.app.domain.model.system.System;
import com.wakabatimes.simplewiki.app.domain.model.system.SystemFactory;
import com.wakabatimes.simplewiki.app.domain.model.system.SystemName;
import com.wakabatimes.simplewiki.app.domain.model.user.*;
import com.wakabatimes.simplewiki.app.domain.service.user.UserService;
import com.wakabatimes.simplewiki.app.interfaces.user.dto.UserResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.user.form.UserInitializeForm;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;

@Controller
@Slf4j
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    UserDetailsService userDetailsService;

    @GetMapping("/")
    public String home(Principal principal, Model model){
        Users users = userService.list();
        if(users.size() == 0){
            return "start/start";
        }

        Authentication auth = (Authentication)principal;
        if(auth != null){
            String name = auth.getName();
            User user = (User) userDetailsService.loadUserByUsername(name);
            UserResponseDto userResponseDto = new UserResponseDto(user);
            model.addAttribute("userInfo",userResponseDto);
            model.addAttribute("user",true);
        }else {
            model.addAttribute("user",false);
        }

        return "home";
    }

    @ResponseBody
    @GetMapping(path="/puml_image", produces = MediaType.IMAGE_PNG_VALUE)
    public HttpEntity<byte[]> puml_image(@RequestParam("url")  String url){
        String decode  = "";
        try {
            decode = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        SourceStringReader reader = new SourceStringReader(decode.replace(';','\n'));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            reader.generateImage(bos, new FileFormatOption(FileFormat.PNG, false));
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        byte[] b = bos.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(b.length);
        return new HttpEntity<byte[]>(b, headers);
    }
}
