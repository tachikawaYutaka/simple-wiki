package com.wakabatimes.simplewiki.app.application.system;

import com.wakabatimes.simplewiki.app.domain.model.system.System;
import com.wakabatimes.simplewiki.app.domain.model.system.SystemRepository;
import com.wakabatimes.simplewiki.app.domain.service.system.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemServiceImpl implements SystemService{
    @Autowired
    SystemRepository systemRepository;

    @Override
    public void save(System system) {
        systemRepository.save(system);
    }

    @Override
    public void update(System system) {
        systemRepository.update(system);
    }

    @Override
    public boolean exist() {
        return systemRepository.exist();
    }

    @Override
    public System get() {
        return systemRepository.get();
    }
}
