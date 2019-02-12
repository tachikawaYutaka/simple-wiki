package com.wakabatimes.simplewiki.app.domain.model.body;

import java.util.Date;
import java.util.UUID;

public class BodyFactory {
    public static Body create(BodyContent bodyContent, BodyHtml bodyHtml){
        BodyId bodyId = new BodyId(UUID.randomUUID().toString());
        BodyType bodyType = BodyType.CURRENT;
        Date date = new Date();
        BodyCreatedDate bodyCreatedDate = new BodyCreatedDate(date);
        return new Body(bodyId, bodyContent,bodyHtml, bodyType, bodyCreatedDate);
    }

    public static Body createNewBody(){
        BodyId bodyId = new BodyId(UUID.randomUUID().toString());
        BodyContent bodyContent = new BodyContent("# ページを編集して内容を登録してください");
        BodyHtml bodyHtml = new BodyHtml("<h1>ページを編集して内容を登録してください</h1>");
        BodyType bodyType = BodyType.CURRENT;
        Date date = new Date();
        BodyCreatedDate bodyCreatedDate = new BodyCreatedDate(date);
        return new Body(bodyId, bodyContent,bodyHtml, bodyType, bodyCreatedDate);
    }
}
