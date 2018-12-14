package com.wakabatimes.simplewiki.view.contents;

import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuLimit;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuName;
import com.wakabatimes.simplewiki.app.domain.model.menu.Menus;
import com.wakabatimes.simplewiki.app.domain.model.user.User;
import com.wakabatimes.simplewiki.app.domain.service.menu.MenuService;
import com.wakabatimes.simplewiki.app.domain.service.user.UserService;
import com.wakabatimes.simplewiki.app.interfaces.menu.dto.MenuResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.user.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MenuService menuService;

    @GetMapping("/contents/public/{menuName}/**")
    public String content_public_page(HttpServletRequest request, @PathVariable String menuName, Model model, Principal principal){
        final String resourcePath = extractPathFromPattern(request);
        System.out.println("- - - - - -");
        System.out.println(resourcePath);
        System.out.println("- - - - - -");

        Authentication auth = (Authentication)principal;
        if(auth != null){
            String name = auth.getName();
            User user = (User) userDetailsService.loadUserByUsername(name);
            UserResponseDto userResponseDto = new UserResponseDto(user);
            model.addAttribute("userInfo",userResponseDto);
            model.addAttribute("user",true);

            //全メニューリストの取得
            Menus menus = menuService.list();
            List<MenuResponseDto> menuResponseDtoList = new ArrayList<>();
            for(Menu menu : menus.list()){
                MenuResponseDto menuResponseDto = new MenuResponseDto(menu);
                menuResponseDtoList.add(menuResponseDto);
            }
            model.addAttribute("menus",menuResponseDtoList);

        }else {
            //限定されたメニューリストの取得
            Menus menus = menuService.listByMenuLimit(MenuLimit.PUBLIC);
            List<MenuResponseDto> menuResponseDtoList = new ArrayList<>();
            for(Menu menu : menus.list()){
                MenuResponseDto menuResponseDto = new MenuResponseDto(menu);
                menuResponseDtoList.add(menuResponseDto);
            }
            model.addAttribute("menus",menuResponseDtoList);
            model.addAttribute("user",false);
        }

        MenuName currentMenuName = new MenuName(menuName);
        Menu current = menuService.get(currentMenuName);
        MenuResponseDto currentMenu = new MenuResponseDto(current);
        model.addAttribute("currentMenu",currentMenu);



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
}
