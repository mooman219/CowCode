package com.gmail.mooman219.bull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet8UpdateHealth;
import net.minecraft.server.PendingConnection;
import net.minecraft.server.PlayerConnection;

import org.apache.commons.lang.Validate;
import org.bson.types.ObjectId;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.craftbukkit.BullData;
import com.gmail.mooman219.frame.MongoHelper;
import com.gmail.mooman219.frame.scoreboard.Board;
import com.gmail.mooman219.frame.scoreboard.BoardDisplayType;
import com.gmail.mooman219.frame.scoreboard.HealthBoard;
import com.gmail.mooman219.frame.tab.Tab;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.handler.database.UploadReason;
import com.gmail.mooman219.module.chat.store.PDChat;
import com.gmail.mooman219.module.chat.store.PLChat;
import com.gmail.mooman219.module.login.store.PDLogin;
import com.gmail.mooman219.module.region.store.PLRegion;
import com.gmail.mooman219.module.rpg.stat.store.PDStat;
import com.gmail.mooman219.module.service.CCService;
import com.gmail.mooman219.module.service.store.PDService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class CDPlayer extends BullData {
    // ▀▀▀▀▀▀▀▀▀▀ Idea for mob health bar
    public final static HealthBoard healthBoard = new HealthBoard("health", Chat.RED + "" + Chat.BOLD + "HP");
    // [+] Data information
    // [ ]---[+] Offline
    public final ObjectId id;
    public final String username;
    // [ ]---[+] Online
    private Player player = null;
    private ExecutorService thread = null;
    private Board sidebar = null;
    private Tab tabList = null;
    // [+] Module information
    // [ ]---[+] Offline
    public PDService serviceData = null;
    public PDLogin loginData = null;
    public PDChat chatData = null;
    public PDStat statData = null;
    // [ ]---[+] Online
    public PLChat chat = null;
    public PLRegion region = null;

    public CDPlayer(ObjectId id, String username) {
        this.id = id;
        this.username = username;

        this.serviceData = new PDService(this);
        this.loginData = new PDLogin(this);
        this.chatData = new PDChat(this);
        this.statData = new PDStat(this);
    }

    public void startup(Player player, PlayerStartupType startupType) {
        switch(startupType) {
        case POST_VERIFY: // This is done in another thread
            thread = Executors.newSingleThreadExecutor();
            /** Live module data to be added **/
            chat = new PLChat(this);
            region = new PLRegion(this);
            /**/
            break;
        case PRE_CREATION:
            this.player = player;
            break;
        case PRE_JOIN:
            healthBoard.addPlayer(this);
            sidebar = new Board(this, username, getOverheadName(), BoardDisplayType.SIDEBAR);
            tabList = new Tab(this);
            break;
        default:
            break;
        }
    }

    public void shutdown(PlayerShutdownType shutdownType) {
        switch(shutdownType) {
        case POST_QUIT:
            thread.shutdownNow().clear();
            sidebar = null;
            tabList = null;
            break;
        case POST_REMOVAL: // This is done in another thread
            player = null;
            /** Live module data to be removed **/
            chat = null;
            region = null;
            /**/
            break;
        default:
            break;
        }
    }

    /*
     * Database
     */

    public void sync(DBObject playerObject) {
        serviceData.sync(MongoHelper.getValue(playerObject, serviceData.getTag(), new BasicDBObject()));
        loginData.sync(MongoHelper.getValue(playerObject, loginData.getTag(), new BasicDBObject()));
        chatData.sync(MongoHelper.getValue(playerObject, chatData.getTag(), new BasicDBObject()));
        statData.sync(MongoHelper.getValue(playerObject, statData.getTag(), new BasicDBObject()));
    }

    public BasicDBObject getTemplate(UploadReason reason) {
        BasicDBObject template = new BasicDBObject();
        template.putAll(serviceData.getTemplate(reason));
        template.putAll(loginData.getTemplate(reason));
        template.putAll(chatData.getTemplate(reason));
        template.putAll(statData.getTemplate(reason));
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

    public String getName() {
        return username;
    }

    public String getOverheadName() {
        return player.getOverheadName();
    }

    public Player getPlayer() {
        Validate.notNull(player, "Null player");
        return player;
    }

    public Board getSidebar() {
        return sidebar;
    }

    public Tab getTab() {
        return tabList;
    }

    public Future<?> runTask(Runnable task) {
        if(thread.isTerminated()) {
            return null;
        } else {
            return thread.submit(task);
        }
    }

    public void sendPacket(final Packet packet) {
        EntityPlayer handle = getHandle();
        if(handle == null) {
            Loader.warning("Null handle for '" + username + "'");
        } else if(packet == null) {
            Loader.warning("Null packet for '" + username + "'");
        } else if(handle.playerConnection == null) {
            Loader.warning("Null connection for '" + username + "'");
        } else {
            handle.playerConnection.sendPacket(packet);
        }
    }

    public void setDisplayName(String name) {
        player.setDisplayName(name + Chat.RESET);
    }

    public String setOverheadName(String name) {
        String oldName = player.getOverheadName();
        if(sidebar != null && getHandle().playerConnection != null) {
            sidebar.modifyTitle(name);
        }
        name = TextHelper.shrink(name);
        player.setOverheadName(name);
        return oldName;
    }

    /**
     * Updates the current player's (health, foodlevel, foodsaturation).<br>
     * Health max = 20, FoodLevel max = 20, FoodSaturation max = 5f
     * @param health
     * @param foodlevel
     * @param foodsaturation
     */
    public void updateStatus(int health, int foodlevel, float foodsaturation) {
        sendPacket(new Packet8UpdateHealth(health, foodlevel, foodsaturation));
    }

    public void setTabListName(String name) {
        player.setPlayerListName(TextHelper.shrink(name));
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

    public static CDPlayer getSafe(Player player) {
        net.minecraft.server.EntityPlayer handle = ((CraftPlayer)player).getHandle();
        return handle.bull_live == null ? null : (CDPlayer) handle.bull_live;
    }

    public static CDPlayer get(Player player) {
        CDPlayer ret = getSafe(player);
        if(ret == null) {
            CCService.MSG.DATAERROR.kick(player);
            Loader.warning("Invalid data on player '" + player.getName() + "'.");
        }
        return ret;
    }

    public static void set(AsyncPlayerPreLoginEvent event, CDPlayer player) {
        if(event.getPendingConnection() != null) {
            ((PendingConnection) event.getPendingConnection()).bull_live = player;
        } else {
            throw new IllegalArgumentException("Unable to bind CDPlayer to login for '" + player.getName() + "'.");
        }
    }

    /**
     * This method will save any of the CDPlayer data then remove it from the Player object.
     * This should be called when the Player leaves to prevent the data from presisting.
     */
    public static void unload(Player player) {
        net.minecraft.server.Entity handle = ((CraftPlayer)player).getHandle();
        if(handle.bull_live != null) {
            handle.bull_live = null;
        }
    }
}
