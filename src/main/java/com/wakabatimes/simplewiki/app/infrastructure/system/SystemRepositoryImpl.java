package com.wakabatimes.simplewiki.app.infrastructure.system;

import com.wakabatimes.simplewiki.app.domain.model.system.System;
import com.wakabatimes.simplewiki.app.domain.model.system.SystemId;
import com.wakabatimes.simplewiki.app.domain.model.system.SystemName;
import com.wakabatimes.simplewiki.app.domain.model.system.SystemRepository;
import com.wakabatimes.simplewiki.app.infrastructure.system.dto.SystemDto;
import com.wakabatimes.simplewiki.app.infrastructure.system.mapper.SystemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SystemRepositoryImpl implements SystemRepository{
    @Autowired
    SystemMapper systemMapper;

    @Override
    public void save(System system) {
        SystemDto input = new SystemDto(system);
        Integer count = systemMapper.count();
        if(count == 0) {
            systemMapper.save(input);
        }else {
            throw new RuntimeException("既にデータが存在します。");
        }
    }

    @Override
    public void update(System system) {
        SystemDto input = new SystemDto(system);
        systemMapper.update(input);
    }

    @Override
    public boolean exist() {
        Integer count = systemMapper.count();
        if(count > 0){
            return true;
        }
        return false;
    }

    @Override
    public System get() {
        SystemDto result = systemMapper.get();
        SystemId systemId = new SystemId(result.getId());
        SystemName systemName = new SystemName(result.getName());
        System system = new System(systemId,systemName);
        return system;
    }
}
