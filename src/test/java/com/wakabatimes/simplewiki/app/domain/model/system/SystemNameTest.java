package com.wakabatimes.simplewiki.app.domain.model.system;

import org.junit.Test;

public class SystemNameTest {
    @Test
    public void createInstance_success() {
        SystemName bodyContent = new SystemName("hogehoge");
    }

    @Test(expected = RuntimeException.class)
    public void createInstance_fail() {
        SystemName bodyContent = new SystemName("aa");
    }

    @Test(expected = RuntimeException.class)
    public void createInstance_fail2() {
        String char22 = "aaaaaaaaaaaaaaaaaaaaaa";
        SystemName bodyContent = new SystemName(char22);
    }

    @Test(expected = RuntimeException.class)
    public void createInstance_fail3() {
        String notPattern = "あああああ";
        SystemName bodyContent = new SystemName(notPattern);
    }
}
