package com.wakabatimes.simplewiki.app.application.main_menu;

import com.wakabatimes.simplewiki.app.domain.aggregates.main_menu.MainMenu;
import com.wakabatimes.simplewiki.app.domain.aggregates.main_menu.MainMenus;
import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuLimit;
import com.wakabatimes.simplewiki.app.domain.model.menu.Menus;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.Pages;
import com.wakabatimes.simplewiki.app.domain.service.main_menu.MainMenuService;
import com.wakabatimes.simplewiki.app.domain.service.menu.MenuService;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import com.wakabatimes.simplewiki.app.interfaces.main_menu.dto.MainMenuResponseDto;
import com.wakabatimes.simplewiki.app.interfaces.page.dto.PageResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainMenuServiceImpl implements MainMenuService{
    @Autowired
    private MenuService menuService;
    @Autowired
    private PageService pageService;

    @Override
    public MainMenus list(){
        MainMenus result = new MainMenus();
        Menus menus = menuService.list();
        for(Menu menu: menus.list()){
            Pages pages = pageService.listRoot(menu.getMenuId());
            MainMenu mainMenu = new MainMenu(menu,pages);
            result.add(mainMenu);
        }
        return result;
    }

    @Override
    public MainMenus listByMenuLimit(MenuLimit menuLimit) {
        MainMenus result = new MainMenus();
        Menus menus = menuService.listByMenuLimit(menuLimit);
        for(Menu menu: menus.list()){
            Pages pages = pageService.listRoot(menu.getMenuId());
            MainMenu mainMenu = new MainMenu(menu,pages);
            result.add(mainMenu);
        }
        return result;
    }
}
