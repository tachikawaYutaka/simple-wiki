package com.wakabatimes.simplewiki.app.domain.aggregates.main_menu;

import com.wakabatimes.simplewiki.app.domain.model.menu.*;
import com.wakabatimes.simplewiki.app.domain.model.page.*;
import com.wakabatimes.simplewiki.app.interfaces.main_menu.dto.MainMenuResponseDto;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MainMenusTest {
    @Test
    public void add() {
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        PageName pageName2 = new PageName("hogehoge2");
        PageType pageType2 = PageType.ROOT;
        Page page2 = PageFactory.create(pageName2,pageType2);

        Pages pages = new Pages();
        pages.add(page);
        pages.add(page2);

        MainMenu mainMenu = new MainMenu(menu,pages);

        MenuName menuName2 = new MenuName("hogehoge");
        MenuLimit menuLimit2 = MenuLimit.PUBLIC;
        Menu menu2 = MenuFactory.create(menuName2,menuLimit2);

        PageName pageName3 = new PageName("hogehoge3");
        PageType pageType3 = PageType.ROOT;
        Page page3 = PageFactory.create(pageName3,pageType3);

        PageName pageName4 = new PageName("hogehoge4");
        PageType pageType4 = PageType.ROOT;
        Page page4 = PageFactory.create(pageName4,pageType4);

        Pages pages2 = new Pages();
        pages.add(page3);
        pages.add(page4);

        MainMenu mainMenu2 = new MainMenu(menu2,pages2);

        MainMenus mainMenus = new MainMenus();
        mainMenus.add(mainMenu);
        mainMenus.add(mainMenu2);

        assertEquals(2, mainMenus.size().intValue());
    }

    @Test
    public void mainMenus() {
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        PageName pageName2 = new PageName("hogehoge2");
        PageType pageType2 = PageType.ROOT;
        Page page2 = PageFactory.create(pageName2,pageType2);

        Pages pages = new Pages();
        pages.add(page);
        pages.add(page2);

        MainMenu mainMenu = new MainMenu(menu,pages);

        MenuName menuName2 = new MenuName("hogehoge");
        MenuLimit menuLimit2 = MenuLimit.PUBLIC;
        Menu menu2 = MenuFactory.create(menuName2,menuLimit2);

        PageName pageName3 = new PageName("hogehoge3");
        PageType pageType3 = PageType.ROOT;
        Page page3 = PageFactory.create(pageName3,pageType3);

        PageName pageName4 = new PageName("hogehoge4");
        PageType pageType4 = PageType.ROOT;
        Page page4 = PageFactory.create(pageName4,pageType4);

        Pages pages2 = new Pages();
        pages.add(page3);
        pages.add(page4);

        MainMenu mainMenu2 = new MainMenu(menu2,pages2);

        List<MainMenu> mainMenuList = new ArrayList<>();
        mainMenuList.add(mainMenu);
        mainMenuList.add(mainMenu2);

        MainMenus mainMenus = new MainMenus(mainMenuList);

        assertEquals(2, mainMenus.size().intValue());
    }

    @Test
    public void list() {
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        PageName pageName2 = new PageName("hogehoge2");
        PageType pageType2 = PageType.ROOT;
        Page page2 = PageFactory.create(pageName2,pageType2);

        Pages pages = new Pages();
        pages.add(page);
        pages.add(page2);

        MainMenu mainMenu = new MainMenu(menu,pages);

        MenuName menuName2 = new MenuName("hogehoge");
        MenuLimit menuLimit2 = MenuLimit.PUBLIC;
        Menu menu2 = MenuFactory.create(menuName2,menuLimit2);

        PageName pageName3 = new PageName("hogehoge3");
        PageType pageType3 = PageType.ROOT;
        Page page3 = PageFactory.create(pageName3,pageType3);

        PageName pageName4 = new PageName("hogehoge4");
        PageType pageType4 = PageType.ROOT;
        Page page4 = PageFactory.create(pageName4,pageType4);

        Pages pages2 = new Pages();
        pages.add(page3);
        pages.add(page4);

        MainMenu mainMenu2 = new MainMenu(menu2,pages2);

        List<MainMenu> mainMenuList = new ArrayList<>();
        mainMenuList.add(mainMenu);
        mainMenuList.add(mainMenu2);

        MainMenus mainMenus = new MainMenus(mainMenuList);

        assertEquals(mainMenuList, mainMenus.list());
    }

    @Test
    public void responseList() {
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        MenuSortNumber menuSortNumber = new MenuSortNumber(1);
        Menu menu = MenuFactory.createWithSort(menuName,menuLimit,menuSortNumber);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        PageSortNumber pageSortNumber = new PageSortNumber(1);
        Page page = PageFactory.createWithSortNumber(pageName,pageType, pageSortNumber);

        PageName pageName2 = new PageName("hogehoge2");
        PageType pageType2 = PageType.ROOT;
        PageSortNumber pageSortNumber2 = new PageSortNumber(2);
        Page page2 = PageFactory.createWithSortNumber(pageName2,pageType2,pageSortNumber2);

        Pages pages = new Pages();
        pages.add(page);
        pages.add(page2);

        MainMenu mainMenu = new MainMenu(menu,pages);

        MenuName menuName2 = new MenuName("hogehoge");
        MenuLimit menuLimit2 = MenuLimit.PUBLIC;
        MenuSortNumber menuSortNumber2 = new MenuSortNumber(2);
        Menu menu2 = MenuFactory.createWithSort(menuName2,menuLimit2,menuSortNumber2);

        PageName pageName3 = new PageName("hogehoge3");
        PageType pageType3 = PageType.ROOT;
        PageSortNumber pageSortNumber3 = new PageSortNumber(1);
        Page page3 = PageFactory.createWithSortNumber(pageName3,pageType3,pageSortNumber3);

        PageName pageName4 = new PageName("hogehoge4");
        PageType pageType4 = PageType.ROOT;
        PageSortNumber pageSortNumber4= new PageSortNumber(2);
        Page page4 = PageFactory.createWithSortNumber(pageName4,pageType4,pageSortNumber4);

        Pages pages2 = new Pages();
        pages.add(page3);
        pages.add(page4);

        MainMenu mainMenu2 = new MainMenu(menu2,pages2);

        List<MainMenu> mainMenuList = new ArrayList<>();
        mainMenuList.add(mainMenu);
        mainMenuList.add(mainMenu2);

        MainMenus mainMenus = new MainMenus(mainMenuList);

        List<MainMenuResponseDto> mainMenuResponseDtos = mainMenus.responseList();

        assertEquals(mainMenuResponseDtos.get(0).getMenuName(), mainMenus.list().get(0).getMenu().getMenuName().getValue());
    }
}
