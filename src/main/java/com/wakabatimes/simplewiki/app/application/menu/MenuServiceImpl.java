package com.wakabatimes.simplewiki.app.application.menu;

import com.wakabatimes.simplewiki.app.domain.model.menu.*;
import com.wakabatimes.simplewiki.app.domain.service.menu.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository menuRepository;

    @Override
    public void save(Menu menu) {
        menuRepository.save(menu);
    }

    @Override
    public void update(Menu menu) {
        menuRepository.update(menu);
    }

    @Override
    public void delete(Menu menu) {
        menuRepository.delete(menu);
    }

    @Override
    public Menus list() {
        return menuRepository.list();
    }

    @Override
    public Menu get(MenuName menuName) {
        return menuRepository.getByMenuName(menuName);
    }

    @Override
    public Menu getById(MenuId menuId) {
        return menuRepository.getById(menuId);
    }

    @Override
    public Menus listByMenuLimit(MenuLimit menuLimit) {
        return menuRepository.listByMenuLimit(menuLimit);
    }

    @Override
    public Menu getHomeMenu() {
        return menuRepository.getHomeMenu();
    }

    @Override
    public void replaceSort(MenuId first, MenuId second) {
        menuRepository.replaceSort(first,second);
    }

}
