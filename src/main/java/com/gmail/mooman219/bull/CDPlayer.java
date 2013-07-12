package com.gmail.mooman219.bull;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet;
import net.minecraft.server.PendingConnection;
import net.minecraft.server.PlayerConnection;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
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
import com.gmail.mooman219.module.login.store.PDLogin;
import com.gmail.mooman219.module.region.store.PDRegion;
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
    // Make sure you add the reference in these places:
    //
    // CDPlayer(String username)
    // .startup(Player player, PlayerStartupType startupType)
    // .shutdown(PlayerShutdownType shutdownType)
    // .sync(DBObject playerObject)
    // .getTemplate(UploadReason reason)
    //
    public PDService service = null;
    public PDLogin login = null;
    public PDChat chat = null;
    public PDStat stat = null;
    public PDRegion region = null;

    public CDPlayer(String username) {
        this.username = username;
        // Create data
        this.service = new PDService(this);
        this.login = new PDLogin(this);
        this.chat = new PDChat(this);
        this.stat = new PDStat(this);
        this.region = new PDRegion(this);
    }

    public void startup(Player player, PlayerStartupType startupType) {
        switch(startupType) {
        case PRELOGIN: // This is done in another thread
            // Create live module data
            service.create();
            login.create();
            chat.create();
            stat.create();
            region.create();
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
            // Remove live module data
            service.destroy();
            login.destroy();
            chat.destroy();
            stat.destroy();
            region.destroy();
            break;
        default:
            break;
        }
    }

    /*
     * Database
     */

    public void sync(DBObject playerObject) {
        service.sync(MongoHelper.getValue(playerObject, service.getTag(), new BasicDBObject()));
        login.sync(MongoHelper.getValue(playerObject, login.getTag(), new BasicDBObject()));
        chat.sync(MongoHelper.getValue(playerObject, chat.getTag(), new BasicDBObject()));
        stat.sync(MongoHelper.getValue(playerObject, stat.getTag(), new BasicDBObject()));
        region.sync(MongoHelper.getValue(playerObject, region.getTag(), new BasicDBObject()));
    }

    public BasicDBObject getTemplate(UploadReason reason) {
        BasicDBObject template = new BasicDBObject();
        template.putAll(service.getTemplate(reason));
        template.putAll(login.getTemplate(reason));
        template.putAll(chat.getTemplate(reason));
        template.putAll(stat.getTemplate(reason));
        template.putAll(region.getTemplate(reason));
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

    public void sendBlockChange(Location location, Material material) {
        player.sendBlockChange(location, material, (byte) 0);
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
