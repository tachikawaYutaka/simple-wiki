package com.wakabatimes.simplewiki.view.contents;

import com.wakabatimes.simplewiki.app.aggregates.PageHierarchyShowService;
import com.wakabatimes.simplewiki.app.domain.model.body.Bodies;
import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageId;
import com.wakabatimes.simplewiki.app.domain.model.user.User;
import com.wakabatimes.simplewiki.app.domain.service.body.BodyService;
import com.wakabatimes.simplewiki.app.domain.service.menu.MenuService;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import com.wakabatimes.simplewiki.app.interfaces.body.dto.BodyResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.menu.dto.MenuResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.page.dto.PageResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.page_hierarchy.dto.PageHierarchyResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.user.dto.UserResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class EditController {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PageService pageService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private BodyService bodyService;

    @Autowired
    private PageHierarchyShowService pageHierarchyShowService;

    @GetMapping("/contents/edit/{menuId}/{pageId}")
    public String edit(@PathVariable String menuId, @PathVariable String pageId, Model model, Principal principal){
        Authentication auth = (Authentication)principal;
        String name = auth.getName();
        User user = (User) userDetailsService.loadUserByUsername(name);
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

        return "contents/edit";
    }
}
