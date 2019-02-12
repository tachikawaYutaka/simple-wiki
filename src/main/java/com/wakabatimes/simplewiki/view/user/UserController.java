package com.wakabatimes.simplewiki.view.user;

import com.wakabatimes.simplewiki.app.domain.model.system.System;
import com.wakabatimes.simplewiki.app.domain.model.user.User;
import com.wakabatimes.simplewiki.app.domain.model.user.UserName;
import com.wakabatimes.simplewiki.app.domain.model.user.UserPassword;
import com.wakabatimes.simplewiki.app.domain.service.original_html.OriginalHtmlService;
import com.wakabatimes.simplewiki.app.domain.service.original_style.OriginalStyleService;
import com.wakabatimes.simplewiki.app.domain.service.system.SystemService;
import com.wakabatimes.simplewiki.app.domain.service.user.UserService;
import com.wakabatimes.simplewiki.app.infrastructure.original_html.dto.OriginalHtmlDto;
import com.wakabatimes.simplewiki.app.infrastructure.original_style.dto.OriginalStyleDto;
import com.wakabatimes.simplewiki.app.interfaces.system.dto.SystemResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.user.dto.UserResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.user.form.UserNameUpdateForm;
import com.wakabatimes.simplewiki.app.interfaces.user.form.UserPasswordUpdateForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Slf4j
@Controller
public class UserController {
    @Autowired
    private SystemService systemService;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private OriginalStyleService originalStyleService;

    @Autowired
    private OriginalHtmlService originalHtmlService;

    @GetMapping("/user/name")
    public String userName(Model model, Principal principal){
        Authentication auth = (Authentication)principal;
        String name = auth.getName();
        UserName userName = new UserName(name);
        User user = userService.get(userName);
        UserResponseDto userResponseDto = new UserResponseDto(user);
        model.addAttribute("userInfo",userResponseDto);
        model.addAttribute("user",true);

        System system = systemService.get();
        SystemResponseDto systemResponseDto = new SystemResponseDto(system);
        model.addAttribute("system",systemResponseDto);

        if(originalHtmlService.exist()){
            OriginalHtmlDto originalHtmlDto = new OriginalHtmlDto(originalHtmlService.get());
            model.addAttribute("originalHtml",originalHtmlDto);
        }
        if(originalStyleService.exist()){
            OriginalStyleDto originalStyleDto = new OriginalStyleDto(originalStyleService.get());
            model.addAttribute("originalStyle",originalStyleDto);
        }
        return "user/user_name";
    }

    @PostMapping("/user/name")
    public String userNameMod(Principal principal, @ModelAttribute UserNameUpdateForm form, RedirectAttributes attr){
        Authentication auth = (Authentication)principal;
        try{
            String name = auth.getName();
            UserName userName = new UserName(name);
            User user = userService.get(userName);
            UserName userName2 = new UserName(form.getUserName());
            userService.nameUpdate(user.getUserName(),userName2);
        }catch(RuntimeException e){
            log.error("Error",e);
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e.getMessage());
            return "redirect:/user/name";
        }
        attr.addFlashAttribute("success",true);
        attr.addFlashAttribute("successMessage","ユーザー名を更新しました。");
        return "redirect:/logout";
    }

    @GetMapping("/user/password")
    public String userPassword(Model model, Principal principal){
        Authentication auth = (Authentication)principal;
        String name = auth.getName();
        UserName userName = new UserName(name);
        User user = userService.get(userName);
        UserResponseDto userResponseDto = new UserResponseDto(user);
        model.addAttribute("userInfo",userResponseDto);
        model.addAttribute("user",true);

        System system = systemService.get();
        SystemResponseDto systemResponseDto = new SystemResponseDto(system);
        model.addAttribute("system",systemResponseDto);

        if(originalHtmlService.exist()){
            OriginalHtmlDto originalHtmlDto = new OriginalHtmlDto(originalHtmlService.get());
            model.addAttribute("originalHtml",originalHtmlDto);
        }
        if(originalStyleService.exist()){
            OriginalStyleDto originalStyleDto = new OriginalStyleDto(originalStyleService.get());
            model.addAttribute("originalStyle",originalStyleDto);
        }
        return "user/user_password";
    }

    @PostMapping("/user/password")
    public String userPasswordMod(Principal principal, @ModelAttribute UserPasswordUpdateForm form, RedirectAttributes attr){
        Authentication auth = (Authentication)principal;
        try{
            String name = auth.getName();
            UserName userName = new UserName(name);
            User user = userService.get(userName);
            UserPassword userPassword = new UserPassword(form.getUserPassword(),bCryptPasswordEncoder);
            userService.passwordUpdate(user.getUserName(),userPassword);
        }catch(RuntimeException e){
            log.error("Error",e);
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e.getMessage());
            return "redirect:/user/password";
        }
        attr.addFlashAttribute("success",true);
        attr.addFlashAttribute("successMessage","パスワードを更新しました。");
        return "redirect:/user/password";
    }
}
