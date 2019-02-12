package com.wakabatimes.simplewiki.view.start;

import com.wakabatimes.simplewiki.app.application.system_initialize.SystemInitializeServiceImpl;
import com.wakabatimes.simplewiki.app.domain.aggregates.sytem_initialize.SystemInitialize;
import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import com.wakabatimes.simplewiki.app.domain.model.body.BodyFactory;
import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuFactory;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuLimit;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuName;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageFactory;
import com.wakabatimes.simplewiki.app.domain.model.page.PageName;
import com.wakabatimes.simplewiki.app.domain.model.page.PageType;
import com.wakabatimes.simplewiki.app.domain.model.system.System;
import com.wakabatimes.simplewiki.app.domain.model.system.SystemFactory;
import com.wakabatimes.simplewiki.app.domain.model.system.SystemName;
import com.wakabatimes.simplewiki.app.domain.model.user.*;
import com.wakabatimes.simplewiki.app.domain.service.system_initialize.SystemInitializeService;
import com.wakabatimes.simplewiki.app.domain.service.user.UserService;
import com.wakabatimes.simplewiki.app.interfaces.user.form.UserInitializeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StartController {

    @Autowired
    private SystemInitializeService systemInitializeService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    @GetMapping("/start")
    public String start(){
        Users users = userService.list();
        if(users.size() > 0){
            return "redirect:/login";
        }
        return "start/start";
    }

    @PostMapping("/start")
    public String initialize(@ModelAttribute UserInitializeForm form, RedirectAttributes attr) {
        try {
            //システムの登録
            SystemName systemName = new SystemName(form.getSystemName());
            System system = SystemFactory.create(systemName);

            //管理者ユーザーの作成
            UserName userName = new UserName(form.getUserName());
            UserPassword userPassword = new UserPassword(form.getUserPassword(), bCryptPasswordEncoder);
            User user = UserFactory.create(userName, userPassword, UserRole.ROLE_ADMIN);

            //メニューの作成
            MenuName menuName = new MenuName("ホーム");
            MenuLimit menuLimit = MenuLimit.PUBLIC;
            Menu menu = MenuFactory.create(menuName,menuLimit);

            //メインページの作成
            PageName pageName = new PageName("メインページ");
            PageType pageType = PageType.ROOT;
            Page page = PageFactory.create(pageName,pageType);

            //ページボディの作成
            Body body = BodyFactory.createNewBody();

            SystemInitialize systemInitialize = new SystemInitialize(system,user,menu,page,body);
            systemInitializeService.save(systemInitialize);
        }catch (RuntimeException e){
            attr.addFlashAttribute("error",true);
            attr.addFlashAttribute("errorMessage",e.getMessage());
            return "redirect:/";
        }

        attr.addFlashAttribute("success",true);
        return "redirect:/login";
    }
}
