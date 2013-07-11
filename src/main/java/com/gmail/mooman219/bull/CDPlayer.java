package com.gmail.mooman219.bull;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet;
import net.minecraft.server.PendingConnection;
import net.minecraft.server.PlayerConnection;

import org.apache.commons.lang.Validate;
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
import com.gmail.mooman219.handler.task.CHTask;
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
    private final String username;
    // [ ]---[+] Online
    private Player player = null;
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

    public CDPlayer(String username) {
        this.username = username;

        this.serviceData = new PDService();
        this.loginData = new PDLogin();
        this.chatData = new PDChat();
        this.statData = new PDStat();
    }

    public void startup(Player player, PlayerStartupType startupType) {
        switch(startupType) {
        case PRELOGIN: // This is done in another thread
            /** Live module data to be added **/
            chat = new PLChat(this);
            region = new PLRegion(this);
            /**/
            break;
        case LOGIN:
            this.player = player;
            break;
        case JOIN:
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
        CHTask.manager.runPlugin(new Runnable() {
            @Override
            public void run() {
                EntityPlayer handle = getHandle();
                if(handle == null) {
                    Loader.warning("Null handle for '" + username + "'");
                    return;
                }
                PlayerConnection target = handle.playerConnection;
                if(target == null) {
                    Loader.warning("Null connection for '" + username + "'");
                    return;
                }
                target.chat(message, true);
            }
        });
    }

    public String getUsername() {
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
            throw new IllegalArgumentException("Unable to bind CDPlayer to login for '" + player.getUsername() + "'.");
        }
    }
}
