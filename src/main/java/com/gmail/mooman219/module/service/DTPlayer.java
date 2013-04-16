package com.gmail.mooman219.module.service;

import org.bson.types.ObjectId;
import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.database.mongo.DatabaseData;
import com.gmail.mooman219.frame.database.mongo.MongoHelper;
import com.gmail.mooman219.frame.database.mongo.UploadType;
import com.gmail.mooman219.module.chat.store.PDChat;
import com.gmail.mooman219.module.login.store.PDLogin;
import com.gmail.mooman219.module.region.store.PLRegion;
import com.gmail.mooman219.module.service.store.PDService;
import com.gmail.mooman219.module.service.store.PLService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class DTPlayer implements DatabaseData {
    // [+] Data information
    public final ObjectId id;
    public final String username;
    // [-]---[+] Module
    public PDService serviceData = null;
    public PDLogin loginData = null;
    public PDChat chatData = null;
    // [+] Live information
    // [-]---[+] Module
    public PLService service = null;
    public PLRegion region = null;

    public DTPlayer(ObjectId id, String username) {
        this.id = id;
        this.username = username;

        this.serviceData = new PDService();
        this.loginData = new PDLogin();
        this.chatData = new PDChat();
    }

    public void sync(DBObject playerObject) {
        serviceData.sync(MongoHelper.getValue(playerObject, serviceData.tag, new BasicDBObject()));
        loginData.sync(MongoHelper.getValue(playerObject, loginData.tag, new BasicDBObject()));
        chatData.sync(MongoHelper.getValue(playerObject, chatData.tag, new BasicDBObject()));
    }

    public BasicDBObject getTemplate(UploadType uploadType) {
        BasicDBObject template = new BasicDBObject();
        template.putAll(serviceData.getTemplate(uploadType));
        template.putAll(loginData.getTemplate(uploadType));
        template.putAll(chatData.getTemplate(uploadType));
        return template;
    }

    public String getTag() {
        return "";
    }

    public static DTPlayer get(Player player) {
        return (DTPlayer) player.getLive();
    }
}
