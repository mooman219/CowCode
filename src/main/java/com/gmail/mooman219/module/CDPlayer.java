package com.gmail.mooman219.module;

import net.minecraft.server.PendingConnection;

import org.bson.types.ObjectId;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import com.gmail.mooman219.craftbukkit.CowData;
import com.gmail.mooman219.craftbukkit.CowTaggable;
import com.gmail.mooman219.frame.TagHelper;
import com.gmail.mooman219.frame.database.mongo.MongoHelper;
import com.gmail.mooman219.frame.database.mongo.UploadType;
import com.gmail.mooman219.module.chat.store.PDChat;
import com.gmail.mooman219.module.chat.store.PLChat;
import com.gmail.mooman219.module.login.store.PDLogin;
import com.gmail.mooman219.module.service.store.PDService;
import com.gmail.mooman219.module.service.store.PLService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class CDPlayer implements CowData {
    // [+] Data information
    public final ObjectId id;
    public final String username;
    // [-]---[+] Module
    public PDService serviceData = null;
    public PDLogin loginData = null;
    public PDChat chatData = null;
    // [+] Live information
    public Player player = null;
    // [-]---[+] Module
    public PLService service = null;
    public PLChat chat = null;

    public CDPlayer(ObjectId id, String username) {
        this.id = id;
        this.username = username;

        this.serviceData = new PDService();
        this.loginData = new PDLogin();
        this.chatData = new PDChat();
    }

    public void initializePlayer(Player player) {
        this.player = player;
        onLoad(getHandle());

        this.service = new PLService();
        this.chat = new PLChat();
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

    /*
     * Tag
     */

    public int test = 0;
    
    @Override
    public void onLoad(CowTaggable handle) {
        test = TagHelper.getInt(handle.dataTag, "test", test);
    }
    
    @Override
    public void onSave(CowTaggable handle) {
        handle.clearStoreTag();
        handle.dataTag.setInt("test", test);
    }

    /*
     * Default
     */

    public net.minecraft.server.EntityPlayer getHandle() {
        return ((CraftPlayer)player).getHandle();
    }

    public static CDPlayer get(Player player) {
        net.minecraft.server.EntityPlayer handle = ((CraftPlayer)player).getHandle();
        if(handle.dataLive == null) {
            throw new IllegalArgumentException("Invalid data on player.");
        }
        CDPlayer ret = (CDPlayer) handle.dataLive;
        if(ret.player == null) {
            ret.initializePlayer(player);
        }
        return ret;
    }

    public static void set(AsyncPlayerPreLoginEvent event, CDPlayer dataPlayer) {
        ((PendingConnection) event.getPendingConnection()).dataLive = dataPlayer;
    }
}
