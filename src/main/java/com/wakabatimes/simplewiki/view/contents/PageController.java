package com.wakabatimes.simplewiki.view.contents;

import com.wakabatimes.simplewiki.app.domain.aggregates.branch_page.BranchPage;
import com.wakabatimes.simplewiki.app.domain.aggregates.main_menu.MainMenus;
import com.wakabatimes.simplewiki.app.domain.aggregates.page_hierarchy.PageHierarchies;
import com.wakabatimes.simplewiki.app.domain.aggregates.root_page.RootPage;
import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuLimit;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuName;
import com.wakabatimes.simplewiki.app.domain.model.page.*;
import com.wakabatimes.simplewiki.app.domain.model.system.System;
import com.wakabatimes.simplewiki.app.domain.model.user.User;
import com.wakabatimes.simplewiki.app.domain.model.user.UserName;
import com.wakabatimes.simplewiki.app.domain.service.body.BodyService;
import com.wakabatimes.simplewiki.app.domain.service.branch_page.BranchPageService;
import com.wakabatimes.simplewiki.app.domain.service.main_menu.MainMenuService;
import com.wakabatimes.simplewiki.app.domain.service.menu.MenuService;
import com.wakabatimes.simplewiki.app.domain.service.original_html.OriginalHtmlService;
import com.wakabatimes.simplewiki.app.domain.service.original_style.OriginalStyleService;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import com.wakabatimes.simplewiki.app.domain.service.page_hierarchy.PageHierarchyService;
import com.wakabatimes.simplewiki.app.domain.service.page_with_body.PageWithBodyService;
import com.wakabatimes.simplewiki.app.domain.service.root_page.RootPageService;
import com.wakabatimes.simplewiki.app.domain.service.system.SystemService;
import com.wakabatimes.simplewiki.app.domain.service.user.UserService;
import com.wakabatimes.simplewiki.app.infrastructure.original_html.dto.OriginalHtmlDto;
import com.wakabatimes.simplewiki.app.infrastructure.original_style.dto.OriginalStyleDto;
import com.wakabatimes.simplewiki.app.interfaces.body.dto.BodyResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.main_menu.dto.MainMenuResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.menu.dto.MenuResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.page.dto.PageResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.page.form.BranchPageSaveForm;
import com.wakabatimes.simplewiki.app.interfaces.page.form.RootPageSaveForm;
import com.wakabatimes.simplewiki.app.interfaces.page_hierarchy.dto.PageHierarchyResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.system.dto.SystemResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.user.dto.UserResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
public class PageController {
    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private PageService pageService;

    @Autowired
    private BodyService bodyService;

    @Autowired
    private MainMenuService mainMenuService;

    @Autowired
    private RootPageService rootPageService;

    @Autowired
    private BranchPageService branchPageService;

    @Autowired
    private PageHierarchyService pageHierarchyService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private OriginalStyleService originalStyleService;

    @Autowired
    private OriginalHtmlService originalHtmlService;

    @Autowired
    private PageWithBodyService pageWithBodyService;

    @GetMapping("/contents/public/{menuName}/**")
    public String contentPublicPage(HttpServletRequest request, @PathVariable String menuName, Model model, Principal principal){
        final String resourcePath = extractPathFromPattern(request);
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

        MenuName currentMenuName = new MenuName(menuName);
        Menu current = menuService.get(currentMenuName);
        MenuResponseDto currentMenu = new MenuResponseDto(current);
        model.addAttribute("currentMenu",currentMenu);

        String[] splitPath = resourcePath.split("/");
        String parentId = "";
        for(String path : splitPath){
            PageName pageName = new PageName(path);
            if(parentId.equals("")){
                RootPage rootPage = rootPageService.getRootPageByName(current.getMenuId(),pageName);
                PageResponseDto rootPageDto = new PageResponseDto(rootPage.getPage());
                parentId = rootPage.getPage().getPageId().getValue();
                model.addAttribute("rootPage",rootPageDto);
            }else {
                PageId parentPageId = new PageId(parentId);
                Page childPage = pageService.getPageByParentAndChildName(parentPageId, pageName);
                parentId = childPage.getPageId().getValue();
            }
        }
        PageId pageId = new PageId(parentId);
        Page currentPage = pageService.get(pageId);
        PageResponseDto pageResponseDto = new PageResponseDto(currentPage);
        model.addAttribute("currentPage",pageResponseDto);

        Body currentBody = bodyService.getCurrent(currentPage.getPageId());
        BodyResponseDto bodyResponseDto = new BodyResponseDto(currentBody);
        model.addAttribute("currentBody",bodyResponseDto);

        PageHierarchies pageHierarchies = pageHierarchyService.getCurrentPath(currentPage.getPageId());
        List<PageHierarchyResponseDto> currentPages = pageHierarchies.responseList();
        model.addAttribute("currentPages",currentPages);

        PageHierarchies pageHierarchies1 = pageHierarchyService.list(current.getMenuId());
        List<PageHierarchyResponseDto> pages = pageHierarchies1.responseList();
        model.addAttribute("pages",pages);

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

        return "contents/page";
    }

    @GetMapping("/contents/private/{menuName}/**")
    public String contentPrivatePage(HttpServletRequest request, @PathVariable String menuName, Model model, Principal principal){
        final String resourcePath = extractPathFromPattern(request);
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

        MenuName currentMenuName = new MenuName(menuName);
        Menu current = menuService.get(currentMenuName);
        MenuResponseDto currentMenu = new MenuResponseDto(current);
        model.addAttribute("currentMenu",currentMenu);

        String[] splitPath = resourcePath.split("/");
        String parentId = "";
        for(String path : splitPath){
            PageName pageName = new PageName(path);
            if(parentId.equals("")){
                RootPage rootPage = rootPageService.getRootPageByName(current.getMenuId(), pageName);
                PageResponseDto rootPageDto = new PageResponseDto(rootPage.getPage());
                parentId = rootPage.getPage().getPageId().getValue();
                model.addAttribute("rootPage",rootPageDto);
            }else {
                PageId parentPageId = new PageId(parentId);
                Page childPage = pageService.getPageByParentAndChildName(parentPageId, pageName);
                parentId = childPage.getPageId().getValue();
            }
        }
        PageId pageId = new PageId(parentId);
        Page currentPage = pageService.get(pageId);
        PageResponseDto pageResponseDto = new PageResponseDto(currentPage);
        model.addAttribute("currentPage",pageResponseDto);

        Body currentBody = bodyService.getCurrent(currentPage.getPageId());
        BodyResponseDto bodyResponseDto = new BodyResponseDto(currentBody);
        model.addAttribute("currentBody",bodyResponseDto);

        PageHierarchies pageHierarchies = pageHierarchyService.getCurrentPath(currentPage.getPageId());
        List<PageHierarchyResponseDto> currentPages = pageHierarchies.responseList();
        model.addAttribute("currentPages",currentPages);

        PageHierarchies pageHierarchies1 = pageHierarchyService.list(current.getMenuId());
        List<PageHierarchyResponseDto> pages = pageHierarchies1.responseList();
        model.addAttribute("pages",pages);

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

        return "contents/page";
    }

    /*
     * リクエストのURLパスから、今回マッチしたパターンを取り除いた文字列を返す。
     */
    private static String extractPathFromPattern(final HttpServletRequest request){
        String path = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String)request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
    }

    @PostMapping("/pages/root")
    public String rootPageSave(@ModelAttribute RootPageSaveForm form, RedirectAttributes attr) throws UnsupportedEncodingException {
        try{
            MenuId menuId = new MenuId(form.getMenuId());
            Menu menu = menuService.getById(menuId);

            PageName pageName = new PageName(form.getName());
            PageType pageType = PageType.ROOT;
            Page page = PageFactory.create(pageName,pageType);

            RootPage rootPage = new RootPage(menu,page);
            rootPageService.save(rootPage);

            attr.addFlashAttribute("success",true);
            attr.addFlashAttribute("successMessage","ページを作成しました");
            return "redirect:/contents/" + menu.getMenuLimit().name().toLowerCase() + '/' + URLEncoder.encode(menu.getMenuName().getValue(),"UTF-8");
        }catch(RuntimeException e){
            log.error("Error :",e);
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e.getMessage());

            Menu menu = menuService.getHomeMenu();
            return "redirect:/contents/" + menu.getMenuLimit().name().toLowerCase() + '/' + URLEncoder.encode(menu.getMenuName().getValue(),"UTF-8");
        }
    }

    @PostMapping("/pages/branch")
    public String branchPageSave(@ModelAttribute BranchPageSaveForm form, RedirectAttributes attr) throws UnsupportedEncodingException {
        try{
            MenuId menuId = new MenuId(form.getMenuId());
            Menu menu = menuService.getById(menuId);

            PageId parentId = new PageId(form.getParentId());
            PageName pageName = new PageName(form.getName());
            PageType pageType = PageType.BRANCH;
            Page page = PageFactory.create(pageName,pageType);
            BranchPage branchPage = new BranchPage(parentId,page);
            branchPageService.save(branchPage);

            attr.addFlashAttribute("success",true);
            attr.addFlashAttribute("successMessage","ページを作成しました");
            return "redirect:/contents/" + menu.getMenuLimit().name().toLowerCase() + '/' + URLEncoder.encode(menu.getMenuName().getValue(),"UTF-8");
        }catch(RuntimeException e){
            log.error("Error :",e);
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e.getMessage());

            Menu menu = menuService.getHomeMenu();
            return "redirect:/contents/" + menu.getMenuLimit().name().toLowerCase() + '/' + URLEncoder.encode(menu.getMenuName().getValue(),"UTF-8");
        }
    }

    @PostMapping("/pages/{menuId}/{pageId}/delete")
    public String pageDelete(@PathVariable String menuId, @PathVariable String pageId, RedirectAttributes attr) throws UnsupportedEncodingException {
        try{
            MenuId menuId1 = new MenuId(menuId);
            Menu menu = menuService.getById(menuId1);

            PageId pageId1 = new PageId(pageId);
            Page page = pageService.get(pageId1);
            pageService.delete(page, menuId1);

            attr.addFlashAttribute("success",true);
            attr.addFlashAttribute("successMessage","ページを削除しました。");
            return "redirect:/contents/" + menu.getMenuLimit().name().toLowerCase() + '/' + URLEncoder.encode(menu.getMenuName().getValue(),"UTF-8");
        }catch(RuntimeException e){
            log.error("Error :",e);
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e.getMessage());

            Menu menu = menuService.getHomeMenu();
            return "redirect:/contents/" + menu.getMenuLimit().name().toLowerCase() + '/' + URLEncoder.encode(menu.getMenuName().getValue(),"UTF-8");
        }
    }

    @PostMapping("/pages/{menuId}/{parentPageId}/new")
    public String pageNew(@PathVariable String menuId, @PathVariable String parentPageId, RedirectAttributes attr) throws UnsupportedEncodingException {
        try{
            MenuId menuId1 = new MenuId(menuId);
            Menu menu = menuService.getById(menuId1);

            PageId parentPageId1 = new PageId(parentPageId);
            Page parentPage = pageService.get(parentPageId1);

            Page page = PageFactory.createNewPage(PageType.BRANCH);

            BranchPage branchPage = new BranchPage(parentPageId1,page);
            branchPageService.save(branchPage);

            PageHierarchies pageHierarchies = pageHierarchyService.getCurrentPath(page.getPageId());
            List<PageHierarchyResponseDto> pagePath = pageHierarchies.responseList();
            String path = "";
            for(PageHierarchyResponseDto pageHierarchyResponseDto: pagePath){
                if(pageHierarchyResponseDto.getId().equals(page.getPageId().getValue())){
                    path = pageHierarchyResponseDto.getPath();
                }
            }
            String pageUrl = "";
            String[] pathList = path.split("/");
            for(String  pathItem : pathList){
                pageUrl += URLEncoder.encode(pathItem.replace("/",""),"UTF-8") + "/";
            }

            attr.addFlashAttribute("success",true);
            attr.addFlashAttribute("successMessage","ページを作成しました。");
            return "redirect:/contents/" + menu.getMenuLimit().name().toLowerCase() + '/' + URLEncoder.encode(menu.getMenuName().getValue(),"UTF-8") +  pageUrl;
        }catch(RuntimeException e){
            log.error("Error :",e);
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e.getMessage());

            Menu menu = menuService.getHomeMenu();
            return "redirect:/contents/" + menu.getMenuLimit().name().toLowerCase() + '/' + URLEncoder.encode(menu.getMenuName().getValue(),"UTF-8");
        }
    }



}
