package com.wakabatimes.simplewiki.view.system;

import com.wakabatimes.simplewiki.app.domain.model.system.System;
import com.wakabatimes.simplewiki.app.domain.model.user.*;
import com.wakabatimes.simplewiki.app.domain.service.system.SystemService;
import com.wakabatimes.simplewiki.app.domain.service.user.UserService;
import com.wakabatimes.simplewiki.app.interfaces.system.dto.SystemResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.user.dto.UserResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.user.form.UserNameUpdateForm;
import com.wakabatimes.simplewiki.app.interfaces.user.form.UserPasswordUpdateForm;
import com.wakabatimes.simplewiki.app.interfaces.user.form.UserSaveForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Controller
public class UserAdminController {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/system/users")
    public String systemUsers(Model model, Principal principal){
        Authentication auth = (Authentication)principal;
        String name = auth.getName();
        User user = (User) userDetailsService.loadUserByUsername(name);
        UserResponseDto userResponseDto = new UserResponseDto(user);
        model.addAttribute("userInfo",userResponseDto);
        model.addAttribute("user",true);

        System system = systemService.get();
        SystemResponseDto systemResponseDto = new SystemResponseDto(system);
        model.addAttribute("system",systemResponseDto);

        Users users = userService.list();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for(User thisUser : users.list()) {
            UserResponseDto userResponseDto1 = new UserResponseDto(thisUser);
            userResponseDtos.add(userResponseDto1);
        }
        model.addAttribute("users",userResponseDtos);

        return "system/system_user";
    }

    @PostMapping("/system/users")
    public String systemUserAdd(@ModelAttribute UserSaveForm form, RedirectAttributes attr){
        try{
            UserName userName = new UserName(form.getUserName());
            UserPassword userPassword = new UserPassword(form.getUserPassword(),bCryptPasswordEncoder);
            UserRole userRole = UserRole.getById(form.getUserRole());
            User user = UserFactory.create(userName,userPassword,userRole);
            userService.save(user);
        }catch(RuntimeException e){
            log.error("Error",e);
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e);
        }
        attr.addFlashAttribute("success",true);
        attr.addFlashAttribute("successMessage","ユーザーを追加しました。");
        return "redirect:/system/users";
    }

    @PostMapping("/system/users/{userId}/userName")
    public String systemUserNameMod(@PathVariable String userId, @ModelAttribute UserNameUpdateForm form, RedirectAttributes attr){
        try{
            UserId userId1 = new UserId(userId);
            User user = userService.getById(userId1);
            UserName oldName = user.getUserName();
            UserName userName = new UserName(form.getUserName());
            userService.nameUpdate(oldName,userName);
        }catch(RuntimeException e){
            log.error("Error",e);
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e);
        }
        attr.addFlashAttribute("success",true);
        attr.addFlashAttribute("successMessage","ユーザー名を更新しました。");
        return "redirect:/system/users";
    }

    @PostMapping("/system/users/{userId}/userPassword")
    public String systemUserPasswordMod(@PathVariable String userId, @ModelAttribute UserPasswordUpdateForm form, RedirectAttributes attr){
        try{
            UserId userId1 = new UserId(userId);
            User user = userService.getById(userId1);
            UserPassword userPassword = new UserPassword(form.getUserPassword(),bCryptPasswordEncoder);
            userService.passwordUpdate(user.getUserName(),userPassword);
        }catch(RuntimeException e){
            log.error("Error",e);
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e);
        }
        attr.addFlashAttribute("success",true);
        attr.addFlashAttribute("successMessage","パスワードを更新しました。");
        return "redirect:/system/users";
    }

    @PostMapping("/system/users/{userId}/delete")
    public String systemUserDelete(@PathVariable String userId, RedirectAttributes attr){
        try{
            UserId userId1 = new UserId(userId);
            User user = userService.getById(userId1);
            userService.delete(user);
        }catch(RuntimeException e){
            log.error("Error",e);
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e);
        }
        attr.addFlashAttribute("success",true);
        attr.addFlashAttribute("successMessage","パスワードを更新しました。");
        return "redirect:/system/users";
    }
}
