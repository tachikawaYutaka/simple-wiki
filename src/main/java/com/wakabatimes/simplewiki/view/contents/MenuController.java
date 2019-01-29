package com.wakabatimes.simplewiki.view.contents;

import com.wakabatimes.simplewiki.app.application.main_menu.MainMenuServiceImpl;
import com.wakabatimes.simplewiki.app.application.page_hierarchy.PageHierarchyServiceImpl;
import com.wakabatimes.simplewiki.app.domain.aggregates.main_menu.MainMenus;
import com.wakabatimes.simplewiki.app.domain.aggregates.page_hierarchy.PageHierarchies;
import com.wakabatimes.simplewiki.app.domain.model.menu.*;
import com.wakabatimes.simplewiki.app.domain.model.page.PageId;
import com.wakabatimes.simplewiki.app.domain.model.system.System;
import com.wakabatimes.simplewiki.app.domain.model.user.User;
import com.wakabatimes.simplewiki.app.domain.model.user.UserName;
import com.wakabatimes.simplewiki.app.domain.service.main_menu.MainMenuService;
import com.wakabatimes.simplewiki.app.domain.service.menu.MenuService;
import com.wakabatimes.simplewiki.app.domain.service.original_html.OriginalHtmlService;
import com.wakabatimes.simplewiki.app.domain.service.original_style.OriginalStyleService;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import com.wakabatimes.simplewiki.app.domain.service.page_hierarchy.PageHierarchyService;
import com.wakabatimes.simplewiki.app.domain.service.system.SystemService;
import com.wakabatimes.simplewiki.app.domain.service.user.UserService;
import com.wakabatimes.simplewiki.app.infrastructure.original_html.dto.OriginalHtmlDto;
import com.wakabatimes.simplewiki.app.infrastructure.original_style.dto.OriginalStyleDto;
import com.wakabatimes.simplewiki.app.interfaces.main_menu.dto.MainMenuResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.menu.dto.MenuResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.menu.form.MenuSaveForm;
import com.wakabatimes.simplewiki.app.interfaces.menu.form.MenuSortForm;
import com.wakabatimes.simplewiki.app.interfaces.menu.form.MenuUpdateForm;
import com.wakabatimes.simplewiki.app.interfaces.page.form.PageSortForm;
import com.wakabatimes.simplewiki.app.interfaces.page_hierarchy.dto.PageHierarchyResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.system.dto.SystemResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.user.dto.UserResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private MenuService menuService;

    @Autowired
    private MainMenuService mainMenuService;

    @Autowired
    private PageService pageService;

    @Autowired
    private PageHierarchyService pageHierarchyService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private OriginalStyleService originalStyleService;

    @Autowired
    private OriginalHtmlService originalHtmlService;

    @GetMapping("/contents/public/{menuName}")
    public String contentPublicMenu(@PathVariable String menuName, Model model, Principal principal){
        Authentication auth = (Authentication)principal;
        if(auth != null){
            String name = auth.getName();
            UserName userName = new UserName(name);
            User user = userService.get(userName);
            UserResponseDto userResponseDto = new UserResponseDto(user);
            model.addAttribute("userInfo",userResponseDto);
            model.addAttribute("user",true);

            //全メニューリスト+ルートページの取得
            MainMenus mainMenus = mainMenuService.list();
            List<MainMenuResponseDto> menuLists = mainMenus.responseList();
            model.addAttribute("menus",menuLists);

        }else {
            //限定されたメニューリストの取得
            MainMenus mainMenus = mainMenuService.listByMenuLimit(MenuLimit.PUBLIC);
            List<MainMenuResponseDto> menuLists = mainMenus.responseList();
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

        PageHierarchies pageHierarchies = pageHierarchyService.list(current.getMenuId());
        List<PageHierarchyResponseDto> pages = pageHierarchies.responseList();
        model.addAttribute("pages",pages);


        if(originalHtmlService.exist()){
            OriginalHtmlDto originalHtmlDto = new OriginalHtmlDto(originalHtmlService.get());
            model.addAttribute("originalHtml",originalHtmlDto);
        }
        if(originalStyleService.exist()){
            OriginalStyleDto originalStyleDto = new OriginalStyleDto(originalStyleService.get());
            model.addAttribute("originalStyle",originalStyleDto);
        }



        return "contents/menu";
    }

    @GetMapping("/contents/private/{menuName}")
    public String contentPrivateMenu(@PathVariable String menuName, Model model, Principal principal){
        Authentication auth = (Authentication)principal;
        String name = auth.getName();
        UserName userName = new UserName(name);
        User user = userService.get(userName);
        UserResponseDto userResponseDto = new UserResponseDto(user);
        model.addAttribute("userInfo",userResponseDto);
        model.addAttribute("user",true);

        //全メニューリスト+ルートページの取得
        MainMenus mainMenus = mainMenuService.list();
        List<MainMenuResponseDto> menuLists = mainMenus.responseList();
        model.addAttribute("menus",menuLists);

        System system = systemService.get();
        SystemResponseDto systemResponseDto = new SystemResponseDto(system);
        model.addAttribute("system",systemResponseDto);

        MenuName currentMenuName = new MenuName(menuName);
        Menu current = menuService.get(currentMenuName);
        MenuResponseDto currentMenu = new MenuResponseDto(current);
        model.addAttribute("currentMenu",currentMenu);

        PageHierarchies pageHierarchies = pageHierarchyService.list(current.getMenuId());
        List<PageHierarchyResponseDto> pages = pageHierarchies.responseList();
        model.addAttribute("pages",pages);

        if(originalHtmlService.exist()){
            OriginalHtmlDto originalHtmlDto = new OriginalHtmlDto(originalHtmlService.get());
            model.addAttribute("originalHtml",originalHtmlDto);
        }
        if(originalStyleService.exist()){
            OriginalStyleDto originalStyleDto = new OriginalStyleDto(originalStyleService.get());
            model.addAttribute("originalStyle",originalStyleDto);
        }

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

    @PostMapping("/menu/{menuId}/sort")
    public String sortMenu(@PathVariable String menuId, @ModelAttribute MenuSortForm form, RedirectAttributes attr) throws UnsupportedEncodingException {
        try{
            MenuId first = new MenuId(form.getFirstMenuId());
            MenuId second = new MenuId(form.getSecondMenuId());
            menuService.replaceSort(first,second);

            MenuId menuId1 = new MenuId(menuId);
            Menu menu = menuService.getById(menuId1);
            attr.addFlashAttribute("success",true);
            attr.addFlashAttribute("successMessage","メニューを並び替えました。");
            return "redirect:/contents/" + menu.getMenuLimit().name().toLowerCase() + '/' + URLEncoder.encode(menu.getMenuName().getValue(),"UTF-8");
        }catch(RuntimeException e){
            log.error("Error :",e);
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e.getMessage());
            Menu home = menuService.getHomeMenu();
            return "redirect:/contents/" + home.getMenuLimit().name().toLowerCase() + '/' + URLEncoder.encode(home.getMenuName().getValue(),"UTF-8");
        }
    }

    @PostMapping("/menu/{menuId}/pages/sort")
    public String sortPage(@PathVariable String menuId, @ModelAttribute PageSortForm form, RedirectAttributes attr) throws UnsupportedEncodingException {
        try{
            PageId firstPageId = new PageId(form.getFirstPageId());
            PageId secondPageId = new PageId(form.getSecondPageId());
            pageService.replaceSort(firstPageId,secondPageId);

            MenuId menuId1 = new MenuId(menuId);
            Menu menu = menuService.getById(menuId1);
            attr.addFlashAttribute("success",true);
            attr.addFlashAttribute("successMessage","ページを並び替えました。");
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
