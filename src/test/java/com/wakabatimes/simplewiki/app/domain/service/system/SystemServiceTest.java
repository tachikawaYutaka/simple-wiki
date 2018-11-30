package com.wakabatimes.simplewiki.app.domain.service.system;

import com.wakabatimes.simplewiki.SimpleWikiApplication;
import com.wakabatimes.simplewiki.app.domain.model.system.System;
import com.wakabatimes.simplewiki.app.domain.model.system.SystemFactory;
import com.wakabatimes.simplewiki.app.domain.model.system.SystemName;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.Is.is;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SimpleWikiApplication.class)
public class SystemServiceTest {
    @Autowired
    SystemService systemService;

    @Autowired
    private JdbcOperations jdbcOperations; // 各テスト前処理用

    @Before
    public void eachBefore() {
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=0");
        jdbcOperations.execute("DELETE FROM system");
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=1");
    }

    @After
    public void eachAfter() {
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=0");
        jdbcOperations.execute("DELETE FROM system");
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=1");
    }

    @Test
    public void save(){
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);
    }

    @Test(expected = RuntimeException.class)
    public void save_fail(){
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        SystemName systemName2 = new SystemName("Simple Wiki");
        System system2 = SystemFactory.create(systemName2);
        systemService.save(system2);
    }

    @Test
    public void update() {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        SystemName newName = new SystemName("Simple Wiki2");
        System newSystem = new System(system.getSystemId(),newName);
        systemService.update(newSystem);

        System getSystem = systemService.get();
        Assert.assertThat(getSystem.getSystemName().getValue(),is("Simple Wiki2"));
    }

    @Test
    public void existTrue() {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        boolean exist = systemService.exist();
        Assert.assertTrue(exist);
    }

    @Test
    public void existFalse() {
        boolean exist = systemService.exist();
        Assert.assertFalse(exist);
    }

    @Test
    public void get() {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        System getSystem = systemService.get();
        Assert.assertThat(getSystem.getSystemName().getValue(),is("Simple Wiki"));
    }
}
