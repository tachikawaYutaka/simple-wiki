package com.wakabatimes.simplewiki.view.search;

import com.wakabatimes.simplewiki.app.domain.aggregates.body_and_page.BodyAndPage;
import com.wakabatimes.simplewiki.app.domain.aggregates.main_menu.MainMenus;
import com.wakabatimes.simplewiki.app.domain.aggregates.page_hierarchy.PageHierarchies;
import com.wakabatimes.simplewiki.app.domain.aggregates.page_with_body.PageWithBodies;
import com.wakabatimes.simplewiki.app.domain.aggregates.root_page.RootPage;
import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuLimit;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageId;
import com.wakabatimes.simplewiki.app.domain.model.page.PageName;
import com.wakabatimes.simplewiki.app.domain.model.search.Search;
import com.wakabatimes.simplewiki.app.domain.model.search.SearchInput;
import com.wakabatimes.simplewiki.app.domain.model.system.System;
import com.wakabatimes.simplewiki.app.domain.model.user.User;
import com.wakabatimes.simplewiki.app.domain.model.user.UserName;
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
import com.wakabatimes.simplewiki.app.interfaces.main_menu.dto.MainMenuResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.page_hierarchy.dto.PageHierarchyResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.page_with_body.dto.PageWithBodyResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.system.dto.SystemResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.user.dto.UserResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class SearchController {
    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MainMenuService mainMenuService;

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

    @Autowired
    private PageService pageService;

    @Autowired
    private RootPageService rootPageService;

    @GetMapping("/search")
    public String search(@RequestParam(name="q",required = false) String input , Model model, Principal principal){
        model.addAttribute("input",input);

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
        if(input != null){
            try{
                List<PageWithBodyResponseDto> results = new ArrayList<>();
                SearchInput searchInput = new SearchInput(input);
                Search search = new Search(searchInput);
                PageWithBodies pageWithBodies = pageWithBodyService.search(search);
                results = pageWithBodies.responseList();
                model.addAttribute("results",results);
            } catch (RuntimeException e) {
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", e.getMessage());
            }
        }

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
        return "search/search";
    }

    @GetMapping("/search/{pageId}")
    public String searchPage(@PathVariable String pageId, RedirectAttributes attr) throws UnsupportedEncodingException {
        try {
            PageId pageId1 = new PageId(pageId);
            PageHierarchies pageHierarchies = pageHierarchyService.getCurrentPath(pageId1);
            String path = "";
            for(PageHierarchyResponseDto pageHierarchyResponseDto: pageHierarchies.responseList()){

                if(pageHierarchyResponseDto.getId().equals(pageId1.getValue())){
                    path = pageHierarchyResponseDto.getPath();
                }
            }
            String pageUrl = "";
            String[] pathList = path.split("/");
            String rootPageName = pathList[1].replace("/","");
            PageName pageName = new PageName(rootPageName);
            Page page = pageService.getRootPageByName(pageName);
            RootPage rootPage = rootPageService.getByPageId(page.getPageId());

            for(String  pathItem : pathList){
                pageUrl += URLEncoder.encode(pathItem.replace("/",""),"UTF-8") + "/";
            }
            return "redirect:/contents/" + rootPage.getMenu().getMenuLimit().name().toLowerCase() + '/' + URLEncoder.encode(rootPage.getMenu().getMenuName().getValue(),"UTF-8") +  pageUrl;
        } catch(RuntimeException e){
            log.error("error: ",e);
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e.getMessage());
            return "redirect:/search";
        }
    }
}
