package com.wakabatimes.simplewiki.app.application.menu;

import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuName;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuRepository;
import com.wakabatimes.simplewiki.app.domain.model.menu.Menus;
import com.wakabatimes.simplewiki.app.domain.service.menu.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuRepository menuRepository;

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
}
