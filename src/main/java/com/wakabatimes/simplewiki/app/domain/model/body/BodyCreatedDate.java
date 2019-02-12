package com.wakabatimes.simplewiki.app.domain.model.body;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * value object
 */
@Slf4j
@Value
public class BodyCreatedDate {
    Date value;

    public BodyCreatedDate(Date value){
        this.value = value;
    }
}
