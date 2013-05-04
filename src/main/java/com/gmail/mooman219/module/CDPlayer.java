package com.gmail.mooman219.module;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.PendingConnection;
import net.minecraft.server.PlayerConnection;
import net.minecraft.server.PlayerInventory;

import org.bson.types.ObjectId;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import com.gmail.mooman219.craftbukkit.CowData;
import com.gmail.mooman219.craftbukkit.CowTaggable;
import com.gmail.mooman219.frame.MongoHelper;
import com.gmail.mooman219.frame.TagHelper;
import com.gmail.mooman219.handler.database.UploadReason;
import com.gmail.mooman219.handler.packet.CHPacket;
import com.gmail.mooman219.handler.task.CHTask;
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

    public BasicDBObject getTemplate(UploadReason reason) {
        BasicDBObject template = new BasicDBObject();
        template.putAll(serviceData.getTemplate(reason));
        template.putAll(loginData.getTemplate(reason));
        template.putAll(chatData.getTemplate(reason));
        return template;
    }
    
    /*
     * Special
     */
    
    public void closeInventory() {
        EntityPlayer handle = getHandle();
        CHPacket.manager.sendCloseWindow(player, handle.activeContainer.windowId); // Tell the player to close their inventory
        handle.activeContainer.transferTo(handle.defaultContainer, (CraftHumanEntity) player); // Tell bukkit to close the inventory
        PlayerInventory playerinventory = handle.inventory; // Drop any items being held while in the inventory
        if (playerinventory.getCarried() != null) {
            handle.drop(playerinventory.getCarried());
            playerinventory.setCarried((ItemStack) null);
        }
        handle.activeContainer = handle.defaultContainer; // Close the inventory
    }
    
    public void chat(final String message) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                PlayerConnection target = getHandle().playerConnection;
                if(target == null) {
                    return;
                }
                target.chat(message, true);
            }
        };
        CHTask.manager.runPlugin(task, true);
    }
    
    /*
     * Live
     */
    
    @Override
    public void onTick(CowTaggable handle) {}

    /*
     * Tag
     */
    
    @Override
    public void onLoad(CowTaggable handle) {
    }
    
    @Override
    public void onSave(CowTaggable handle) {
        handle.clearStoreTag();
    }

    /*
     * Default
     */

    public EntityPlayer getHandle() {
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
