package com.wakabatimes.simplewiki.app.domain.model.body;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum BodyType {
    CURRENT(0),
    ARCHIVE(1);

    @Getter
    private Integer id;

    private BodyType(Integer id) {
        this.id = id;
    }

    public static BodyType getById(Integer id) {
        for(BodyType bodyType : BodyType.values()){
            if(bodyType.id == id){
                return bodyType;
            }
        }
        return BodyType.ARCHIVE;
    }
}
