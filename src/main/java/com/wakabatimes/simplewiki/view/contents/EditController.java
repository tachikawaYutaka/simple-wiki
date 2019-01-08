package com.wakabatimes.simplewiki.view.contents;

import com.wakabatimes.simplewiki.app.aggregates.BodyAndPageSaveService;
import com.wakabatimes.simplewiki.app.aggregates.MainMenuShowService;
import com.wakabatimes.simplewiki.app.aggregates.PageHierarchyShowService;
import com.wakabatimes.simplewiki.app.domain.model.body.*;
import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuName;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageId;
import com.wakabatimes.simplewiki.app.domain.model.page.PageName;
import com.wakabatimes.simplewiki.app.domain.model.system.System;
import com.wakabatimes.simplewiki.app.domain.model.user.User;
import com.wakabatimes.simplewiki.app.domain.model.user.UserName;
import com.wakabatimes.simplewiki.app.domain.service.body.BodyService;
import com.wakabatimes.simplewiki.app.domain.service.menu.MenuService;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import com.wakabatimes.simplewiki.app.domain.service.system.SystemService;
import com.wakabatimes.simplewiki.app.domain.service.user.UserService;
import com.wakabatimes.simplewiki.app.interfaces.body.dto.BodyResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.body.form.BodySaveForm;
import com.wakabatimes.simplewiki.app.interfaces.main_menu.dto.MainMenuResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.menu.dto.MenuResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.page.dto.PageResponseDto;
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

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class EditController {
    @Autowired
    private UserService userService;

    @Autowired
    private PageService pageService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private BodyService bodyService;

    @Autowired
    private PageHierarchyShowService pageHierarchyShowService;

    @Autowired
    private MainMenuShowService mainMenuShowService;

    @Autowired
    private BodyAndPageSaveService bodyAndPageSaveService;

    @Autowired
    private SystemService systemService;

    @GetMapping("/contents/edit/{menuId}/{pageId}")
    public String edit(@PathVariable String menuId, @PathVariable String pageId, Model model, Principal principal){
        Authentication auth = (Authentication)principal;
        String name = auth.getName();
        UserName userName = new UserName(name);
        User user = userService.get(userName);
        UserResponseDto userResponseDto = new UserResponseDto(user);
        model.addAttribute("userInfo",userResponseDto);
        model.addAttribute("user",true);

        MenuId menuId1 = new MenuId(menuId);
        Menu menu = menuService.getById(menuId1);
        MenuResponseDto menuResponseDto = new MenuResponseDto(menu);
        model.addAttribute("menu",menuResponseDto);

        PageId pageId1 = new PageId(pageId);
        Page page = pageService.get(pageId1);
        PageResponseDto pageResponseDto = new PageResponseDto(page);
        model.addAttribute("page",pageResponseDto);

        Body body = bodyService.getCurrent(pageId1);
        BodyResponseDto bodyResponseDto = new BodyResponseDto(body);
        model.addAttribute("body",bodyResponseDto);

        List<PageHierarchyResponseDto> currentPages = pageHierarchyShowService.getCurrentPath(pageId1);
        model.addAttribute("currentPages",currentPages);

        Bodies bodies = bodyService.getArchive(pageId1);
        List<BodyResponseDto> bodyArchive = new ArrayList<>();
        for(Body body1 : bodies.list()){
            BodyResponseDto bodyResponseDto1 = new BodyResponseDto(body1);
            bodyArchive.add(bodyResponseDto1);
        }
        model.addAttribute("bodyArchive",bodyArchive);

        System system = systemService.get();
        SystemResponseDto systemResponseDto = new SystemResponseDto(system);
        model.addAttribute("system",systemResponseDto);

        return "contents/edit";
    }

    @PostMapping("/contents/edit/{menuId}/{pageId}")
    public String editSave(@PathVariable String menuId, @PathVariable String pageId, @ModelAttribute BodySaveForm form, RedirectAttributes attr, Principal principal){
        Authentication auth = (Authentication)principal;
        String name = auth.getName();
        UserName userName = new UserName(name);
        User user = userService.get(userName);
        try{
            PageId pageId1 = new PageId(pageId);
            Page page = pageService.get(pageId1);
            PageName pageName = new PageName(form.getPageName());
            Page newPage = new Page(pageId1,pageName,page.getPageType());

            MenuId menuId1 = new MenuId(menuId);

            BodyContent bodyContent = new BodyContent(form.getContent());
            BodyHtml bodyHtml = new BodyHtml(form.getHtml());
            Body body = BodyFactory.create(bodyContent,bodyHtml);

            bodyAndPageSaveService.save(body,menuId1, newPage);
        }catch(RuntimeException e) {
            log.error("error:",e);
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e);
            return "redirect:/contents/edit/" + menuId + "/" + pageId;
        }
        attr.addFlashAttribute("success",true);
        attr.addFlashAttribute("successMessage","ボディを更新しました。");
        return "redirect:/contents/edit/" + menuId + "/" + pageId;
    }

    @GetMapping("/contents/edit/{menuId}/{pageId}/{bodyId}")
    public String preview(@PathVariable String menuId, @PathVariable String pageId,@PathVariable String bodyId, Model model, Principal principal){
        Authentication auth = (Authentication)principal;
        String name = auth.getName();
        UserName userName = new UserName(name);
        User user = userService.get(userName);
        UserResponseDto userResponseDto = new UserResponseDto(user);
        model.addAttribute("userInfo",userResponseDto);
        model.addAttribute("user",true);

        //全メニューリスト+ルートページの取得
        List<MainMenuResponseDto> menuLists = mainMenuShowService.list();
        model.addAttribute("menus",menuLists);

        MenuId menuId1 = new MenuId(menuId);
        Menu menu = menuService.getById(menuId1);
        MenuResponseDto menuResponseDto = new MenuResponseDto(menu);
        model.addAttribute("menu",menuResponseDto);

        PageId pageId1 = new PageId(pageId);
        Page page = pageService.get(pageId1);
        PageResponseDto pageResponseDto = new PageResponseDto(page);
        model.addAttribute("page",pageResponseDto);

        BodyId bodyId1 = new BodyId(bodyId);
        Body body = bodyService.get(bodyId1);
        BodyResponseDto bodyResponseDto = new BodyResponseDto(body);
        model.addAttribute("body",bodyResponseDto);

        List<PageHierarchyResponseDto> currentPages = pageHierarchyShowService.getCurrentPath(pageId1);
        model.addAttribute("currentPages",currentPages);

        System system = systemService.get();
        SystemResponseDto systemResponseDto = new SystemResponseDto(system);
        model.addAttribute("system",systemResponseDto);

        return "contents/preview";
    }

    @PostMapping("/contents/edit/{menuId}/{pageId}/{bodyId}")
    public String restore(@PathVariable String menuId, @PathVariable String pageId,@PathVariable String bodyId,RedirectAttributes attr, Principal principal){
        try {
            BodyId bodyId1 = new BodyId(bodyId);
            Body body = bodyService.get(bodyId1);
            BodyContent bodyContent = body.getBodyContent();
            BodyHtml bodyHtml = body.getBodyHtml();
            Body newBody = BodyFactory.create(bodyContent,bodyHtml);

            PageId pageId1 = new PageId(pageId);
            bodyService.save(newBody, pageId1);
        }catch(RuntimeException e) {
            log.error("error: ", e);
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e);
            return "redirect:/contents/edit/" + menuId + "/" + pageId;
        }

        attr.addFlashAttribute("success",true);
        attr.addFlashAttribute("successMessage","過去のバージョンを復元しました。");
        return "redirect:/contents/edit/" + menuId + "/" + pageId;
    }

    @PostMapping("/contents/edit/{menuId}/{pageId}/{bodyId}/delete")
    public String delete(@PathVariable String menuId, @PathVariable String pageId,@PathVariable String bodyId,RedirectAttributes attr, Principal principal){
        try {
            BodyId bodyId1 = new BodyId(bodyId);
            Body body = bodyService.get(bodyId1);
            bodyService.delete(body);
        }catch(RuntimeException e) {
            log.error("error: ", e);
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e);
            return "redirect:/contents/edit/" + menuId + "/" + pageId;
        }

        attr.addFlashAttribute("success",true);
        attr.addFlashAttribute("successMessage","過去のバージョンを削除しました。");
        return "redirect:/contents/edit/" + menuId + "/" + pageId;
    }
}
