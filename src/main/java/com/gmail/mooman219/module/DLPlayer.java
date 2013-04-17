package com.gmail.mooman219.module;

import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.PendingConnection;

import org.bson.types.ObjectId;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import com.gmail.mooman219.frame.TagHelper;
import com.gmail.mooman219.frame.database.mongo.MongoHelper;
import com.gmail.mooman219.frame.database.mongo.UploadType;
import com.gmail.mooman219.module.chat.store.PDChat;
import com.gmail.mooman219.module.login.store.PDLogin;
import com.gmail.mooman219.module.region.store.PLRegion;
import com.gmail.mooman219.module.service.store.PDService;
import com.gmail.mooman219.module.service.store.PLService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class DLPlayer {
    // [+] Data information
    public final ObjectId id;
    public final String username;
    // [-]---[+] Module
    public PDService serviceData = null;
    public PDLogin loginData = null;
    public PDChat chatData = null;
    // [+] Live information
    public Player player;
    // [-]---[+] Module
    public PLService service = null;
    public PLRegion region = null;

    public DLPlayer(ObjectId id, String username) {
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

    /*
     * Tag
     */
    
    private int test = 0;
    
    public void setTest(int test) {
    	this.test = test;
    	getHandle().dataTag.setInt("test", test);
    }
    
    public int getTest() {
    	return test;
    }
    
    public void loadTag() {
    	NBTTagCompound tag = getHandle().dataTag;
    	test = TagHelper.getInt(tag, "test", test);
    }
    
    /*
     * Default
     */
    
    public net.minecraft.server.EntityPlayer getHandle() {
    	return ((CraftPlayer)player).getHandle();
    }
    
    public static DLPlayer get(Player player) {
        net.minecraft.server.EntityPlayer handle = ((CraftPlayer)player).getHandle();
        if(handle.dataLive == null || !(handle.dataLive instanceof DLPlayer)) {
            throw new IllegalArgumentException("Invalid data on player.");
        }
        DLPlayer ret = (DLPlayer) handle.dataLive;
        if(ret.player == null) {
        	ret.player = player;
        	ret.loadTag();
        }
        return ret;
    }
    
    public static void set(Player player, DLPlayer dataPlayer) {
        ((CraftPlayer)player).getHandle().dataLive = dataPlayer;
    }
    
    public static void set(AsyncPlayerPreLoginEvent event, DLPlayer dataPlayer) {
        ((PendingConnection) event.getPendingConnection()).dataLive = dataPlayer;
    }
}
