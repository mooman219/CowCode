package com.gmail.mooman219.module;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
import com.gmail.mooman219.frame.scoreboard.Board;
import com.gmail.mooman219.frame.scoreboard.BoardDisplayType;
import com.gmail.mooman219.frame.tab.Tab;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.handler.database.UploadReason;
import com.gmail.mooman219.handler.packet.CHPacket;
import com.gmail.mooman219.module.chat.store.PDChat;
import com.gmail.mooman219.module.chat.store.PLChat;
import com.gmail.mooman219.module.login.store.PDLogin;
import com.gmail.mooman219.module.service.store.PDService;
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
    private ExecutorService thread = null;
    private Board sidebar = null;
    private Board nametagbar = null;
    private Tab tabList = null;
    // [+] Loading information
    private boolean initialized = false;
    // [-]---[+] Module
    public PLChat chat = null;

    public CDPlayer(ObjectId id, String username) {
        this.id = id;
        this.username = username;

        this.serviceData = new PDService();
        this.loginData = new PDLogin();
        this.chatData = new PDChat();
    }

    /*
     * Special
     */

    public void initializePlayer(Player player) {
        // This is fired normally in Login
        if(this.player == null) {
            this.player = player;
            this.thread = Executors.newSingleThreadExecutor();
            onLoad(getHandle());

            this.chat = new PLChat();
        }
        // This is fired after Login
        if(getHandle().playerConnection != null) {
            this.sidebar = new Board(player, username, serviceData.rank.color + username, BoardDisplayType.SIDEBAR);
            this.nametagbar = new Board(player, "nametag", "NametagTest", BoardDisplayType.BELOWNAME);
            // ▀▀▀▀▀▀▀▀▀▀
            this.tabList = new Tab(player);
            
            this.initialized = true;
        }
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
     * Live
     */

    public void chat(final String message) {
        CDPlayer.get(player).runTask(new Runnable() {
            @Override
            public void run() {
                PlayerConnection target = getHandle().playerConnection;
                if(target == null) {
                    return;
                }
                target.chat(message, true);
            }
        });
    }

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

    public Board getSidebar() {
        return sidebar;
    }
    
    public Board getNametagBar() {
        return nametagbar;
    }

    public Tab getTab() {
        return tabList;
    }

    public void setDisplayName(String name) {
        player.setDisplayName(name + Chat.RESET);
    }

    public void setTabListName(String name) {
        player.setPlayerListName(TextHelper.shrink(name));
    }
    
    public Future<?> runTask(Runnable task) {
        return thread.submit(task);
    }

    /*
     * Tag
     */

    @Override
    public void onTick(CowTaggable handle) {}

    @Override
    public void onLoad(CowTaggable handle) {}

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
        if(!ret.initialized) {
            ret.initializePlayer(player);
        }
        return ret;
    }

    public static void set(AsyncPlayerPreLoginEvent event, CDPlayer dataPlayer) {
        ((PendingConnection) event.getPendingConnection()).dataLive = dataPlayer;
    }
}
