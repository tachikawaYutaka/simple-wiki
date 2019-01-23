package com.wakabatimes.simplewiki.view.system;

import com.wakabatimes.simplewiki.app.domain.model.original_html.OriginalHtml;
import com.wakabatimes.simplewiki.app.domain.model.original_html.OriginalHtmlBody;
import com.wakabatimes.simplewiki.app.domain.model.original_html.OriginalHtmlFactory;
import com.wakabatimes.simplewiki.app.domain.model.original_style.OriginalStyle;
import com.wakabatimes.simplewiki.app.domain.model.original_style.OriginalStyleBody;
import com.wakabatimes.simplewiki.app.domain.model.original_style.OriginalStyleFactory;
import com.wakabatimes.simplewiki.app.domain.model.system.System;
import com.wakabatimes.simplewiki.app.domain.model.system.SystemName;
import com.wakabatimes.simplewiki.app.domain.model.user.User;
import com.wakabatimes.simplewiki.app.domain.model.user.UserName;
import com.wakabatimes.simplewiki.app.domain.model.user.Users;
import com.wakabatimes.simplewiki.app.domain.service.original_html.OriginalHtmlService;
import com.wakabatimes.simplewiki.app.domain.service.original_style.OriginalStyleService;
import com.wakabatimes.simplewiki.app.domain.service.system.SystemService;
import com.wakabatimes.simplewiki.app.domain.service.user.UserService;
import com.wakabatimes.simplewiki.app.infrastructure.original_html.dto.OriginalHtmlDto;
import com.wakabatimes.simplewiki.app.infrastructure.original_style.dto.OriginalStyleDto;
import com.wakabatimes.simplewiki.app.interfaces.original_html.dto.OriginalHtmlResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.original_html.form.OriginalHtmlUpdateForm;
import com.wakabatimes.simplewiki.app.interfaces.original_style.dto.OriginalStyleResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.original_style.form.OriginalStyleUpdateForm;
import com.wakabatimes.simplewiki.app.interfaces.system.dto.SystemResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.system.form.SystemNameUpdateForm;
import com.wakabatimes.simplewiki.app.interfaces.user.dto.UserResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class SystemController {
    @Autowired
    private UserService userService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private OriginalStyleService originalStyleService;

    @Autowired
    private OriginalHtmlService originalHtmlService;

    @GetMapping("/system/name")
    public String systemName(Model model, Principal principal){
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

        return "system/system_name";
    }

    @PostMapping("/system/name")
    public String systemNameUpdate(@ModelAttribute SystemNameUpdateForm form, RedirectAttributes attr, Model model){
        try {
            SystemName systemName = new SystemName(form.getSystemName());
            System system = systemService.get();
            System update = new System(system.getSystemId(),systemName);
            systemService.update(update);
        } catch(RuntimeException e) {
            log.error("error: ", e);
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e.getMessage());
            return "redirect:/system/name";
        }
        attr.addFlashAttribute("success",true);
        attr.addFlashAttribute("successMessage","システム名称を更新しました。");
        return "redirect:/system/name";
    }

    @GetMapping("/system/style")
    public String systemStyle(Model model, Principal principal){
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

        if(originalStyleService.exist()){
            OriginalStyle originalStyle = originalStyleService.get();
            OriginalStyleResponseDto originalStyleResponseDto = new OriginalStyleResponseDto(originalStyle);
            model.addAttribute("style",originalStyleResponseDto);
        }

        if(originalHtmlService.exist()){
            OriginalHtmlDto originalHtmlDto = new OriginalHtmlDto(originalHtmlService.get());
            model.addAttribute("originalHtml",originalHtmlDto);
        }
        if(originalStyleService.exist()){
            OriginalStyleDto originalStyleDto = new OriginalStyleDto(originalStyleService.get());
            model.addAttribute("originalStyle",originalStyleDto);
        }

        return "system/system_style";
    }

    @PostMapping("/system/style")
    public String systemStyleUpdate(@ModelAttribute OriginalStyleUpdateForm form, RedirectAttributes attr, Model model){
        try {
            OriginalStyleBody originalStyleBody = new OriginalStyleBody(form.getBody());
            if(originalStyleService.exist()){
                OriginalStyle originalStyle = originalStyleService.get();
                OriginalStyle update = new OriginalStyle(originalStyle.getOriginalStyleId(),originalStyleBody);
                originalStyleService.update(update);
            }else {
                OriginalStyle originalStyle = OriginalStyleFactory.create(originalStyleBody);
                originalStyleService.save(originalStyle);
            }
        } catch(RuntimeException e) {
            log.error("error: ", e);
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e.getMessage());
            return "redirect:/system/style";
        }
        attr.addFlashAttribute("success",true);
        attr.addFlashAttribute("successMessage","スタイル設定を更新しました。");
        return "redirect:/system/style";
    }

    @GetMapping("/system/html")
    public String systemHtml(Model model, Principal principal){
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
            OriginalHtml originalHtml = originalHtmlService.get();
            OriginalHtmlResponseDto originalHtmlResponseDto = new OriginalHtmlResponseDto(originalHtml);
            model.addAttribute("html",originalHtmlResponseDto);
        }

        if(originalHtmlService.exist()){
            OriginalHtmlDto originalHtmlDto = new OriginalHtmlDto(originalHtmlService.get());
            model.addAttribute("originalHtml",originalHtmlDto);
        }
        if(originalStyleService.exist()){
            OriginalStyleDto originalStyleDto = new OriginalStyleDto(originalStyleService.get());
            model.addAttribute("originalStyle",originalStyleDto);
        }

        return "system/system_html";
    }

    @PostMapping("/system/html")
    public String systemHtmlUpdate(@ModelAttribute OriginalHtmlUpdateForm form, RedirectAttributes attr, Model model){
        try {
            OriginalHtmlBody originalHtmlBody = new OriginalHtmlBody(form.getBody());
            if(originalHtmlService.exist()){
                OriginalHtml originalHtml = originalHtmlService.get();
                OriginalHtml update = new OriginalHtml(originalHtml.getOriginalHtmlId(),originalHtmlBody);
                originalHtmlService.update(update);
            }else {
                OriginalHtml originalHtml = OriginalHtmlFactory.create(originalHtmlBody);
                originalHtmlService.save(originalHtml);
            }
        } catch(RuntimeException e) {
            log.error("error: ", e);
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e.getMessage());
            return "redirect:/system/html";
        }
        attr.addFlashAttribute("success",true);
        attr.addFlashAttribute("successMessage","HTML設定を更新しました。");
        return "redirect:/system/html";
    }


}
