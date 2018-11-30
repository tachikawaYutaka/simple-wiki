package com.wakabatimes.simplewiki.app.domain.service.original_html;

import com.wakabatimes.simplewiki.SimpleWikiApplication;
import com.wakabatimes.simplewiki.app.domain.model.original_html.OriginalHtml;
import com.wakabatimes.simplewiki.app.domain.model.original_html.OriginalHtmlBody;
import com.wakabatimes.simplewiki.app.domain.model.original_html.OriginalHtmlFactory;
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
public class OriginalHtmlServiceTest {
    @Autowired
    OriginalHtmlService originalHtmlService;

    @Autowired
    private JdbcOperations jdbcOperations; // 各テスト前処理用

    @Before
    public void eachBefore() {
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=0");
        jdbcOperations.execute("DELETE FROM original_html");
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=1");
    }

    @After
    public void eachAfter() {
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=0");
        jdbcOperations.execute("DELETE FROM original_html");
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=1");
    }

    @Test
    public void save(){
        OriginalHtmlBody originalHtmlBody = new OriginalHtmlBody("hogehoge");
        OriginalHtml originalHtml = OriginalHtmlFactory.create(originalHtmlBody);
        originalHtmlService.save(originalHtml);
    }

    @Test(expected = RuntimeException.class)
    public void save_fail(){
        OriginalHtmlBody originalHtmlBody = new OriginalHtmlBody("hogehoge");
        OriginalHtml originalHtml = OriginalHtmlFactory.create(originalHtmlBody);
        originalHtmlService.save(originalHtml);

        OriginalHtmlBody originalHtmlBody2 = new OriginalHtmlBody("hogehoge");
        OriginalHtml originalHtml2 = OriginalHtmlFactory.create(originalHtmlBody2);
        originalHtmlService.save(originalHtml2);
    }

    @Test
    public void update() {
        OriginalHtmlBody originalHtmlBody = new OriginalHtmlBody("hogehoge");
        OriginalHtml originalHtml = OriginalHtmlFactory.create(originalHtmlBody);
        originalHtmlService.save(originalHtml);

        OriginalHtmlBody newBody = new OriginalHtmlBody("hogehoge2");
        OriginalHtml newHtml = new OriginalHtml(originalHtml.getOriginalHtmlId(),newBody);
        originalHtmlService.update(newHtml);

        OriginalHtml getHtml = originalHtmlService.get();
        Assert.assertThat(getHtml.getOriginalHtmlBody().getValue(),is("hogehoge2"));
    }

    @Test
    public void delete() {
        OriginalHtmlBody originalHtmlBody = new OriginalHtmlBody("hogehoge");
        OriginalHtml originalHtml = OriginalHtmlFactory.create(originalHtmlBody);
        originalHtmlService.save(originalHtml);

        originalHtmlService.delete();

        boolean exist = originalHtmlService.exist();
        Assert.assertFalse(exist);
    }

    @Test(expected = RuntimeException.class)
    public void delete_fail() {
        originalHtmlService.delete();
    }

    @Test
    public void existTrue() {
        OriginalHtmlBody originalHtmlBody = new OriginalHtmlBody("hogehoge");
        OriginalHtml originalHtml = OriginalHtmlFactory.create(originalHtmlBody);
        originalHtmlService.save(originalHtml);
        boolean exist = originalHtmlService.exist();
        Assert.assertTrue(exist);
    }

    @Test
    public void  existFalse() {
        boolean exist = originalHtmlService.exist();
        Assert.assertFalse(exist);
    }

    @Test
    public void get() {
        OriginalHtmlBody originalHtmlBody = new OriginalHtmlBody("hogehoge");
        OriginalHtml originalHtml = OriginalHtmlFactory.create(originalHtmlBody);
        originalHtmlService.save(originalHtml);

        OriginalHtml getHtml = originalHtmlService.get();
        Assert.assertThat(getHtml.getOriginalHtmlBody().getValue(),is("hogehoge"));
    }
}
