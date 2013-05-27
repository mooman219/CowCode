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

import com.gmail.mooman219.craftbukkit.BullData;
import com.gmail.mooman219.frame.MongoHelper;
import com.gmail.mooman219.frame.scoreboard.Board;
import com.gmail.mooman219.frame.scoreboard.BoardDisplayType;
import com.gmail.mooman219.frame.scoreboard.GlobalBoard;
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

public class CDPlayer extends BullData {
    public final static GlobalBoard healthBoard = new GlobalBoard("health", "♥", BoardDisplayType.BELOWNAME);
    // [+] Data information
    public final ObjectId id;
    public final String username;
    // [-]---[+] Module
    public PDService serviceData = null;
    public PDLogin loginData = null;
    public PDChat chatData = null;
    // [+] Live information
    private Player player = null;
    private ExecutorService thread = null;
    private Board sidebar = null;
    private Tab tabList = null;
    // [-]---[+] Loading information
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

    // ▀▀▀▀▀▀▀▀▀▀
    private void initializePlayer(Player player) {
        // This is fired normally in Login, once there is a player.
        if(this.player == null) {
            this.player = player;
            this.thread = Executors.newSingleThreadExecutor();

            this.chat = new PLChat();
        }
        // This is fired after Login, once the play can be sent packets.
        if(getHandle().playerConnection != null) {
            healthBoard.addPlayer(this);
            this.sidebar = new Board(this, username, serviceData.rank.color + username, BoardDisplayType.SIDEBAR);
            this.tabList = new Tab(this);
            
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
    
    public String getName() {
        return username;
    }
    
    public Player getPlayer() {
        return player;
    }

    /*
     * Event
     */

    /*
     * Default
     */

    public EntityPlayer getHandle() {
        return ((CraftPlayer)player).getHandle();
    }

    public static CDPlayer get(Player player) {
        net.minecraft.server.EntityPlayer handle = ((CraftPlayer)player).getHandle();
        if(handle.bull_live == null) {
            throw new IllegalArgumentException("Invalid data on player.");
        }
        CDPlayer ret = (CDPlayer) handle.bull_live;
        if(!ret.initialized) {
            ret.initializePlayer(player);
        }
        return ret;
    }

    public static void set(AsyncPlayerPreLoginEvent event, CDPlayer dataPlayer) {
        ((PendingConnection) event.getPendingConnection()).bull_live = dataPlayer;
    }
}
