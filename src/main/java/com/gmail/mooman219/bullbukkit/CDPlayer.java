package com.gmail.mooman219.bullbukkit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Packet20NamedEntitySpawn;
import net.minecraft.server.Packet29DestroyEntity;
import net.minecraft.server.PendingConnection;
import net.minecraft.server.PlayerConnection;
import net.minecraft.server.PlayerInventory;

import org.bson.types.ObjectId;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.craftbukkit.BullData;
import com.gmail.mooman219.frame.MongoHelper;
import com.gmail.mooman219.frame.scoreboard.Board;
import com.gmail.mooman219.frame.scoreboard.BoardDisplayType;
import com.gmail.mooman219.frame.scoreboard.FastHealthBoard;
import com.gmail.mooman219.frame.tab.Tab;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.handler.database.UploadReason;
import com.gmail.mooman219.handler.packet.CHPacket;
import com.gmail.mooman219.module.chat.store.PDChat;
import com.gmail.mooman219.module.chat.store.PLChat;
import com.gmail.mooman219.module.login.store.PDLogin;
import com.gmail.mooman219.module.service.CCService;
import com.gmail.mooman219.module.service.store.PDService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class CDPlayer extends BullData {
    // ▀▀▀▀▀▀▀▀▀▀ Idea for mob health bar
    public final static FastHealthBoard healthBoard = new FastHealthBoard("health", Chat.RED + "" + Chat.BOLD + "HP"); // The slow one makes the client run faster
    // [+] Data information
    // [ ]---[+] Offline
    public final ObjectId id;
    public final String username;
    // [ ]---[+] Online
    private Player player = null;
    private ExecutorService thread = null;
    private Board sidebar = null;
    private Tab tabList = null;
    private boolean[] loaded = {false, false, false};
    // [+] Module information
    // [ ]---[+] Offline
    public PDService serviceData = null;
    public PDLogin loginData = null;
    public PDChat chatData = null;
    // [ ]---[+] Online
    public PLChat chat = null;

    public CDPlayer(ObjectId id, String username) {
        this.id = id;
        this.username = username;

        this.serviceData = new PDService();
        this.loginData = new PDLogin();
        this.chatData = new PDChat();
    }
    
    public void startup(Player player, PlayerStartupType startupType) {
        switch(startupType) {
        case POST_VERIFY:
            thread = Executors.newSingleThreadExecutor();
            /** Live module data to be added **/
                chat = new PLChat();
            /**/
            loaded[0] = true;
            break;
        case PRE_CREATION:
            this.player = player;
            loaded[1] = true;
            break;
        case PRE_JOIN:
            healthBoard.addPlayer(this);
            sidebar = new Board(this, username, getOverheadName(), BoardDisplayType.SIDEBAR);
            tabList = new Tab(this);
            loaded[2] = true;
            break;
        default:
            break;
        }
    }

    // Shutdown is done in another thread
    public void shutdown() {
        if(loaded[0]) {
            thread.shutdown();
            /** Live module data to be removed **/
                chat = null;
            /**/
        }
        if(loaded[1]) {
            player = null;
        }
        if(loaded[2]) {
            healthBoard.removePlayer(this);
            sidebar = null;
            tabList = null;
        }
    }

    /*
     * Database
     */

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
        runTask(new Runnable() {
            @Override
            public void run() {
                PlayerConnection target = getHandle().playerConnection;
                if(target == null) {
                    Loader.warning("Null connection for '" + username + "'");
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
    
    public String getOverheadName() {
        return this.getHandle().overheadName;
    }

    public String setOverheadName(String name) {
        EntityPlayer handle = getHandle();
        String oldName = handle.overheadName;
        handle.overheadName = name;
        if(handle.playerConnection != null) {
            for(Player other : player.getWorld().getPlayers()) {
                if(other.getEntityId() == player.getEntityId() || other.getLocation().distanceSquared(player.getLocation()) > 57600) { // 240 Blocks // 15 Chunks
                    continue;
                }
                EntityPlayer otherHandle = ((CraftPlayer)other).getHandle();
                otherHandle.playerConnection.sendPacket(new Packet29DestroyEntity(handle.id));
                otherHandle.playerConnection.sendPacket(new Packet20NamedEntitySpawn(handle));
            }
        }
        return oldName;
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
            CCService.MSG.DATAERROR.send(player);
            throw new IllegalArgumentException("Invalid data on player.");
        }
        return (CDPlayer) handle.bull_live;
    }

    public static void set(AsyncPlayerPreLoginEvent event, CDPlayer player) {
        ((PendingConnection) event.getPendingConnection()).bull_live = player;
    }
}
