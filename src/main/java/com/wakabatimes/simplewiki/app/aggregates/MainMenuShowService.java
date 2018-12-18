package com.wakabatimes.simplewiki.app.aggregates;

import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuLimit;
import com.wakabatimes.simplewiki.app.domain.model.menu.Menus;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.Pages;
import com.wakabatimes.simplewiki.app.domain.service.menu.MenuService;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import com.wakabatimes.simplewiki.app.interfaces.main_menu.dto.MainMenuResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.page.dto.PageResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainMenuShowService {
    @Autowired
    private MenuService menuService;
    @Autowired
    private PageService pageService;

    public List<MainMenuResponseDto> list(){
        List<MainMenuResponseDto> result = new ArrayList<>();
        Menus menus = menuService.list();
        for(Menu menu: menus.list()){
            MainMenuResponseDto menuResponseDto = new MainMenuResponseDto(menu);
            Pages pages = pageService.listRoot(menu.getMenuId());
            List<PageResponseDto> resultPages = new ArrayList<>();
            for(Page page : pages.list()) {
                PageResponseDto resultPage = new PageResponseDto(page);
                resultPages.add(resultPage);
            }
            menuResponseDto.setPages(resultPages);
            result.add(menuResponseDto);
        }
        return result;
    }

    public List<MainMenuResponseDto> listByMenuLimit(MenuLimit menuLimit) {
        List<MainMenuResponseDto> result = new ArrayList<>();
        Menus menus = menuService.listByMenuLimit(menuLimit);
        for(Menu menu: menus.list()){
            MainMenuResponseDto menuResponseDto = new MainMenuResponseDto(menu);
            Pages pages = pageService.listRoot(menu.getMenuId());
            List<PageResponseDto> resultPages = new ArrayList<>();
            for(Page page : pages.list()) {
                PageResponseDto resultPage = new PageResponseDto(page);
                resultPages.add(resultPage);
            }
            menuResponseDto.setPages(resultPages);
            result.add(menuResponseDto);
        }
        return result;
    }
}
