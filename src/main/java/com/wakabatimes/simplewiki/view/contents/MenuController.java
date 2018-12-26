package com.wakabatimes.simplewiki.view.contents;

import com.wakabatimes.simplewiki.app.aggregates.MainMenuShowService;
import com.wakabatimes.simplewiki.app.aggregates.PageHierarchyShowService;
import com.wakabatimes.simplewiki.app.domain.model.menu.*;
import com.wakabatimes.simplewiki.app.domain.model.system.System;
import com.wakabatimes.simplewiki.app.domain.model.user.User;
import com.wakabatimes.simplewiki.app.domain.service.menu.MenuService;
import com.wakabatimes.simplewiki.app.domain.service.system.SystemService;
import com.wakabatimes.simplewiki.app.domain.service.user.UserService;
import com.wakabatimes.simplewiki.app.interfaces.main_menu.dto.MainMenuResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.menu.dto.MenuResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.menu.form.MenuSaveForm;
import com.wakabatimes.simplewiki.app.interfaces.menu.form.MenuUpdateForm;
import com.wakabatimes.simplewiki.app.interfaces.page_hierarchy.dto.PageHierarchyResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.system.dto.SystemResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.user.dto.UserResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.List;
@Slf4j
@Controller
public class MenuController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MainMenuShowService mainMenuShowService;

    @Autowired
    private PageHierarchyShowService pageHierarchyShowService;

    @Autowired
    private SystemService systemService;

    @GetMapping("/contents/public/{menuName}")
    public String contentPublicMenu(@PathVariable String menuName, Model model, Principal principal){
        Authentication auth = (Authentication)principal;
        if(auth != null){
            String name = auth.getName();
            User user = (User) userDetailsService.loadUserByUsername(name);
            UserResponseDto userResponseDto = new UserResponseDto(user);
            model.addAttribute("userInfo",userResponseDto);
            model.addAttribute("user",true);

            //全メニューリスト+ルートページの取得
            List<MainMenuResponseDto> menuLists = mainMenuShowService.list();
            model.addAttribute("menus",menuLists);

        }else {
            //限定されたメニューリストの取得
            List<MainMenuResponseDto> menuLists = mainMenuShowService.listByMenuLimit(MenuLimit.PUBLIC);
            model.addAttribute("menus",menuLists);
            model.addAttribute("user",false);
        }

        System system = systemService.get();
        SystemResponseDto systemResponseDto = new SystemResponseDto(system);
        model.addAttribute("system",systemResponseDto);

        MenuName currentMenuName = new MenuName(menuName);
        Menu current = menuService.get(currentMenuName);
        MenuResponseDto currentMenu = new MenuResponseDto(current);
        model.addAttribute("currentMenu",currentMenu);

        List<PageHierarchyResponseDto> pages = pageHierarchyShowService.list(current.getMenuId());
        model.addAttribute("pages",pages);

        return "contents/menu";
    }

    @GetMapping("/contents/private/{menuName}")
    public String contentPrivateMenu(@PathVariable String menuName, Model model, Principal principal){
        Authentication auth = (Authentication)principal;
        String name = auth.getName();
        User user = (User) userDetailsService.loadUserByUsername(name);
        UserResponseDto userResponseDto = new UserResponseDto(user);
        model.addAttribute("userInfo",userResponseDto);
        model.addAttribute("user",true);

        //全メニューリスト+ルートページの取得
        List<MainMenuResponseDto> menuLists = mainMenuShowService.list();
        model.addAttribute("menus",menuLists);

        System system = systemService.get();
        SystemResponseDto systemResponseDto = new SystemResponseDto(system);
        model.addAttribute("system",systemResponseDto);

        MenuName currentMenuName = new MenuName(menuName);
        Menu current = menuService.get(currentMenuName);
        MenuResponseDto currentMenu = new MenuResponseDto(current);
        model.addAttribute("currentMenu",currentMenu);

        List<PageHierarchyResponseDto> pages = pageHierarchyShowService.list(current.getMenuId());
        model.addAttribute("pages",pages);

        return "contents/menu";
    }

    @PostMapping("/menu")
    public String menuSave(@ModelAttribute MenuSaveForm menuSaveForm, RedirectAttributes attr) throws UnsupportedEncodingException {
        try{
            MenuName menuName = new MenuName(menuSaveForm.getName());
            MenuLimit menuLimit = MenuLimit.getById(menuSaveForm.getViewLimit());
            Menu menu = MenuFactory.create(menuName,menuLimit);
            menuService.save(menu);

            attr.addFlashAttribute("success",true);
            attr.addFlashAttribute("successMessage","メニューを作成しました");
            return "redirect:/contents/" + menu.getMenuLimit().name().toLowerCase() + '/' + URLEncoder.encode(menu.getMenuName().getValue(),"UTF-8");
        }catch(RuntimeException e){
            log.error("Error :",e);
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e.getMessage());

            Menu home = menuService.getHomeMenu();
            return "redirect:/contents/" + home.getMenuLimit().name().toLowerCase() + '/' + URLEncoder.encode(home.getMenuName().getValue(),"UTF-8");
        }
    }

    @PostMapping("/menu/{menuId}/delete")
    public String menuDelete(@PathVariable String menuId, RedirectAttributes attr) throws UnsupportedEncodingException {
        try{
            MenuId menuId1 = new MenuId(menuId);
            Menu menu = menuService.getById(menuId1);
            menuService.delete(menu);
            Menu home = menuService.getHomeMenu();
            attr.addFlashAttribute("success",true);
            attr.addFlashAttribute("successMessage","メニューを削除しました");
            return "redirect:/contents/" + home.getMenuLimit().name().toLowerCase() + '/' + URLEncoder.encode(home.getMenuName().getValue(),"UTF-8");
        }catch(RuntimeException e){
            log.error("Error :",e);
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e.getMessage());

            Menu home = menuService.getHomeMenu();
            return "redirect:/contents/" + home.getMenuLimit().name().toLowerCase() + '/' + URLEncoder.encode(home.getMenuName().getValue(),"UTF-8");
        }
    }

    @PostMapping("/menu/{menuId}/update")
    public String menuUpdate(@PathVariable String menuId, @ModelAttribute MenuUpdateForm form, RedirectAttributes attr) throws UnsupportedEncodingException {
        try{
            MenuId menuId1 = new MenuId(menuId);
            MenuName menuName = new MenuName(form.getMenuName());
            MenuLimit menuLimit = MenuLimit.getById(form.getMenuViewLimit());
            Menu menu = new Menu(menuId1,menuName,menuLimit);
            menuService.update(menu);
            attr.addFlashAttribute("success",true);
            attr.addFlashAttribute("successMessage","メニューを更新しました");
            return "redirect:/contents/" + menu.getMenuLimit().name().toLowerCase() + '/' + URLEncoder.encode(menu.getMenuName().getValue(),"UTF-8");
        }catch(RuntimeException e){
            log.error("Error :",e);
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e.getMessage());
            Menu home = menuService.getHomeMenu();
            return "redirect:/contents/" + home.getMenuLimit().name().toLowerCase() + '/' + URLEncoder.encode(home.getMenuName().getValue(),"UTF-8");
        }
    }
}
