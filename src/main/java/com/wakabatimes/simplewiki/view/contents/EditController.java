package com.wakabatimes.simplewiki.view.contents;

import com.wakabatimes.simplewiki.app.application.body_and_page.BodyAndPageServiceImpl;
import com.wakabatimes.simplewiki.app.application.main_menu.MainMenuServiceImpl;
import com.wakabatimes.simplewiki.app.application.page_hierarchy.PageHierarchyServiceImpl;
import com.wakabatimes.simplewiki.app.domain.aggregates.body_and_page.BodyAndPage;
import com.wakabatimes.simplewiki.app.domain.aggregates.main_menu.MainMenus;
import com.wakabatimes.simplewiki.app.domain.aggregates.page_hierarchy.PageHierarchies;
import com.wakabatimes.simplewiki.app.domain.aggregates.page_hierarchy.PageHierarchy;
import com.wakabatimes.simplewiki.app.domain.model.body.*;
import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageId;
import com.wakabatimes.simplewiki.app.domain.model.page.PageName;
import com.wakabatimes.simplewiki.app.domain.model.system.System;
import com.wakabatimes.simplewiki.app.domain.model.user.User;
import com.wakabatimes.simplewiki.app.domain.model.user.UserName;
import com.wakabatimes.simplewiki.app.domain.service.body.BodyService;
import com.wakabatimes.simplewiki.app.domain.service.body_and_page.BodyAndPageService;
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
    private PageHierarchyService pageHierarchyService;

    @Autowired
    private MainMenuService mainMenuService;

    @Autowired
    private BodyAndPageService bodyAndPageService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private OriginalHtmlService originalHtmlService;
    @Autowired
    private OriginalStyleService originalStyleService;

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

        PageHierarchies currentPages = pageHierarchyService.getCurrentPath(pageId1);
        List<PageHierarchyResponseDto> currentPageList = currentPages.responseList();
        model.addAttribute("currentPages",currentPageList);

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

        if(originalHtmlService.exist()){
            OriginalHtmlDto originalHtmlDto = new OriginalHtmlDto(originalHtmlService.get());
            model.addAttribute("originalHtml",originalHtmlDto);
        }
        if(originalStyleService.exist()){
            OriginalStyleDto originalStyleDto = new OriginalStyleDto(originalStyleService.get());
            model.addAttribute("originalStyle",originalStyleDto);
        }

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
            BodyAndPage bodyAndPage = new BodyAndPage(menuId1,newPage,body);
            bodyAndPageService.save(bodyAndPage);
        }catch(RuntimeException e) {
            log.error("error:",e);
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e.getMessage());
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
        MainMenus  menuLists = mainMenuService.list();
        List<MainMenuResponseDto> menuResponseList = menuLists.responseList();
        model.addAttribute("menus",menuResponseList);

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

        PageHierarchies pageHierarchies = pageHierarchyService.getCurrentPath(pageId1);
        List<PageHierarchyResponseDto> currentPages = pageHierarchies.responseList();
        model.addAttribute("currentPages",currentPages);

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
            attr.addFlashAttribute("errorMessage",e.getMessage());
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
            attr.addFlashAttribute("errorMessage",e.getMessage());
            return "redirect:/contents/edit/" + menuId + "/" + pageId;
        }

        attr.addFlashAttribute("success",true);
        attr.addFlashAttribute("successMessage","過去のバージョンを削除しました。");
        return "redirect:/contents/edit/" + menuId + "/" + pageId;
    }
}
