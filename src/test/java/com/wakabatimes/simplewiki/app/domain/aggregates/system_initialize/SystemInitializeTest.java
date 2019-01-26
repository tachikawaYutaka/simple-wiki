package com.wakabatimes.simplewiki.app.domain.aggregates.system_initialize;

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
import com.wakabatimes.simplewiki.app.domain.model.user.User;
import com.wakabatimes.simplewiki.app.domain.model.user.UserFactory;
import com.wakabatimes.simplewiki.app.domain.model.user.UserName;
import com.wakabatimes.simplewiki.app.domain.model.user.UserPassword;
import org.junit.Assert;
import org.junit.Test;

public class SystemInitializeTest {
    @Test
    public void createInsntace_success() {
        SystemName bodyContent = new SystemName("hogehoge");
        System system = SystemFactory.create(bodyContent);

        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");

        User user = UserFactory.create(userName, userPassword);

        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        Body body = BodyFactory.createNewBody();

        SystemInitialize systemInitialize = new SystemInitialize(system,user,menu,page,body);

        Assert.assertNotNull(systemInitialize);
    }

}
