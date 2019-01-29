package com.wakabatimes.simplewiki.app.infrastructure.menu;

import com.wakabatimes.simplewiki.app.domain.model.menu.*;
import com.wakabatimes.simplewiki.app.infrastructure.menu.dto.MenuDto;
import com.wakabatimes.simplewiki.app.infrastructure.menu.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuRepositoryImpl implements MenuRepository {
    @Autowired
    MenuMapper menuMapper;

    @Override
    public void save(Menu menu) {
        MenuDto input = new MenuDto(menu);
        List<MenuDto> menuDtoList = menuMapper.list();
        Menus menus = new Menus();
        int i = 0;
        for(MenuDto menuDto : menuDtoList) {
            MenuId menuId = new MenuId(menuDto.getId());
            MenuName menuName = new MenuName(menuDto.getName());
            MenuLimit menuLimit = MenuLimit.getById(menuDto.getViewLimit());
            MenuSortNumber menuSortNumber = new MenuSortNumber(menuDto.getSortNumber());
            Menu thisMenu = new Menu(menuId,menuName,menuLimit, menuSortNumber);
            menus.add(thisMenu);
            i++;
        }

        if(!menus.containsName(menu)) {
            input.setSortNumber(i + 1);
            menuMapper.save(input);
        }else {
            throw new RuntimeException("メニューが既に登録されています。");
        }
    }

    @Override
    public void update(Menu menu) {
        MenuDto input = new MenuDto(menu);
        List<MenuDto> menuDtoList = menuMapper.list();
        Menus menus = new Menus();
        for(MenuDto menuDto : menuDtoList) {
            MenuId menuId = new MenuId(menuDto.getId());
            MenuName menuName = new MenuName(menuDto.getName());
            MenuLimit menuLimit = MenuLimit.getById(menuDto.getViewLimit());
            MenuSortNumber menuSortNumber = new MenuSortNumber(menuDto.getSortNumber());
            Menu thisMenu = new Menu(menuId,menuName,menuLimit, menuSortNumber);
            menus.add(thisMenu);
        }

        if(!menus.containsName(menu)) {
            menuMapper.update(input);
        }else {
            throw new RuntimeException("メニューが既に登録されています。");
        }
    }

    @Override
    public void delete(Menu menu) {
        MenuDto input = new MenuDto(menu);
        MenuDto homeMenu = menuMapper.getHomeMenu();
        if(input.getId().equals(homeMenu.getId())){
            throw new RuntimeException("ホームメニューは削除できません。");
        }else{
            menuMapper.delete(input);
        }
    }

    @Override
    public Menus list() {
        List<MenuDto> menuDtoList = menuMapper.list();
        Menus menus = new Menus();
        for(MenuDto menuDto : menuDtoList) {
            MenuId menuId = new MenuId(menuDto.getId());
            MenuName menuName = new MenuName(menuDto.getName());
            MenuLimit menuLimit = MenuLimit.getById(menuDto.getViewLimit());
            MenuSortNumber menuSortNumber = new MenuSortNumber(menuDto.getSortNumber());
            Menu thisMenu = new Menu(menuId,menuName,menuLimit, menuSortNumber);
            menus.add(thisMenu);
        }
        return menus;
    }

    @Override
    public Menu getByMenuName(MenuName menuName) {
        MenuDto input = new MenuDto();
        input.setName(menuName.getValue());
        MenuDto result = menuMapper.getByMenuName(input);
        MenuId menuId = new MenuId(result.getId());
        MenuName menuName1 = new MenuName(result.getName());
        MenuLimit menuLimit = MenuLimit.getById(result.getViewLimit());
        MenuSortNumber menuSortNumber = new MenuSortNumber(result.getSortNumber());
        Menu menu = new Menu(menuId,menuName1,menuLimit,menuSortNumber);
        return menu;
    }

    @Override
    public Menus listByMenuLimit(MenuLimit menuLimit) {
        MenuDto input = new MenuDto();
        input.setViewLimit(menuLimit.getId());
        List<MenuDto> menuDtoList = menuMapper.listByMenuLimit(input);
        Menus menus = new Menus();
        for(MenuDto menuDto : menuDtoList) {
            MenuId menuId = new MenuId(menuDto.getId());
            MenuName menuName = new MenuName(menuDto.getName());
            MenuLimit menuLimit1 = MenuLimit.getById(menuDto.getViewLimit());
            MenuSortNumber menuSortNumber = new MenuSortNumber(menuDto.getSortNumber());
            Menu thisMenu = new Menu(menuId,menuName,menuLimit1, menuSortNumber);
            menus.add(thisMenu);
        }
        return menus;
    }

    @Override
    public Menu getById(MenuId menuId) {
        MenuDto input = new MenuDto();
        input.setId(menuId.getValue());
        MenuDto result = menuMapper.getById(input);
        if(result != null){
            MenuId menuId1 = new MenuId(result.getId());
            MenuName menuName = new MenuName(result.getName());
            MenuLimit menuLimit = MenuLimit.getById(result.getViewLimit());
            MenuSortNumber menuSortNumber = new MenuSortNumber(result.getSortNumber());
            Menu menu = new Menu(menuId1,menuName,menuLimit,menuSortNumber);
            return menu;
        }else {
            throw new RuntimeException("指定したメニューが存在しません。");
        }
    }

    @Override
    public Menu getHomeMenu() {
        MenuDto result = menuMapper.getHomeMenu();
        MenuId menuId1 = new MenuId(result.getId());
        MenuName menuName = new MenuName(result.getName());
        MenuLimit menuLimit = MenuLimit.getById(result.getViewLimit());
        MenuSortNumber menuSortNumber = new MenuSortNumber(result.getSortNumber());
        Menu menu = new Menu(menuId1,menuName,menuLimit,menuSortNumber);
        return menu;
    }

    @Override
    public void replaceSort(MenuId first, MenuId second) {
        MenuDto firstInput = new MenuDto();
        firstInput.setId(first.getValue());
        MenuDto firstResult = menuMapper.getById(firstInput);
        Integer firstSort = firstResult.getSortNumber();

        MenuDto secondInput = new MenuDto();
        secondInput.setId(second.getValue());
        MenuDto secondResult = menuMapper.getById(secondInput);
        Integer secondSort = secondResult.getSortNumber();

        MenuDto menuFirst = new MenuDto();
        menuFirst.setId(firstResult.getId());
        menuFirst.setSortNumber(secondSort);
        menuMapper.updateSort(menuFirst);

        MenuDto menuSecond = new MenuDto();
        menuSecond.setId(secondResult.getId());
        menuSecond.setSortNumber(firstSort);
        menuMapper.updateSort(menuSecond);
    }

}
