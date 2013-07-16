package com.gmail.mooman219.bull;

import java.util.ArrayList;

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
import com.gmail.mooman219.frame.tab.Tab;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.handler.database.UploadReason;
import com.gmail.mooman219.handler.task.CHTask;
import com.gmail.mooman219.layout.Damageable;
import com.gmail.mooman219.layout.PlayerData;
import com.gmail.mooman219.module.chat.store.PDChat;
import com.gmail.mooman219.module.damage.CCDamage;
import com.gmail.mooman219.module.login.store.PDLogin;
import com.gmail.mooman219.module.region.store.PDRegion;
import com.gmail.mooman219.module.service.CCService;
import com.gmail.mooman219.module.service.store.PDService;
import com.gmail.mooman219.module.stat.store.PDStat;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class CDPlayer extends BullData implements Damageable {
    // [+] Data information
    // [ ]---[+] Offline
    private final String username;
    // [ ]---[+] Online
    private Player player = null;
    private Board sidebar = null;
    private Tab tabList = null;
    // [+] Module information
    private ArrayList<PlayerData> playerData = null;
    public PDService service = null;
    public PDLogin login = null;
    public PDChat chat = null;
    public PDStat stat = null;
    public PDRegion region = null;

    public CDPlayer(String username) {
        this.username = username;
        // Create data
        this.playerData = new ArrayList<PlayerData>();
        this.service = addPlayerData(new PDService(this));
        this.login = addPlayerData(new PDLogin(this));
        this.chat = addPlayerData(new PDChat(this));
        this.stat = addPlayerData(new PDStat(this));
        this.region = addPlayerData(new PDRegion(this));
    }

    /**
     * Management
     */

    /**
     * Marks the PlayerData as active so it can be called when needed
     */
    private <T extends PlayerData> T addPlayerData(T data) {
        this.playerData.add(data);
        return data;
    }

    /**
     * DO THIS AFTER YOUR DONE WITH ALL DATA.
     * Otherwise, you cannot save/load/doanything with the playerdata after this is called.
     * As of right now, only the player upload code calls this AFTER it's completely down uploading it.
     */
    public void clearPlayerData() {
        playerData.clear();
    }

    public void startup(Player player, PlayerStartupType startupType) {
        switch(startupType) {
        case PRELOGIN: // This is done in another thread
            for(PlayerData playerdata : playerData) {
                playerdata.create();
            }
            break;
        case LOGIN:
            this.player = player;
            break;
        case JOIN:
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
            for(PlayerData data : playerData) {
                data.destroy();
            }
            break;
        default:
            break;
        }
    }

    /*
     * Database
     */

    /**
     * Returns true if it failed.
     */
    public boolean sync(DBObject playerObject) {
        for(PlayerData data : playerData) {
            try {
                data.sync(MongoHelper.getValue(playerObject, data.getTag(), new BasicDBObject()));
            } catch(Exception e) {
                Loader.warning("Error in sync() for " + username + ".");
                e.printStackTrace();
                return true;
            }
        }
        return false;
    }

    public BasicDBObject getTemplate(UploadReason reason) {
        BasicDBObject template = new BasicDBObject();
        for(PlayerData data : playerData) {
            try {
                template.putAll(data.getTemplate(reason));
            } catch(Exception e) {
                Loader.warning("Error in getTemplate() for " + username + ".");
                e.printStackTrace();
            }
        }
        return template;
    }

    /**
     * Damageable
     */

    /**
     * Matches the players hearts to the current health state.
     * Also updates the HealthBoard. This will NEVER kill a player.
     */
    public void updateHealth(boolean isDamage) {
        double health = (stat.healthCur / stat.healthMax) * 20D;
        health = health <= 0 ? 1 : health;
        if(!player.isDead()) {
            if(isDamage) {
                player.damage(0);
            }
            player.setHealth(health);
        }
        sidebar.modifyName("hp", CCDamage.FRM.BARHEALTH.parse(stat.healthCur));
        CCDamage.healthBoard.updatePlayer(this);
    }

    @Override
    public void damage(double amount) {
        setHealth(stat.healthCur - amount);
    }

    @Override
    public double getHealth() {
        return stat.healthCur;
    }

    @Override
    public long getLastDamaged() {
        return stat.getLastDamaged();
    }

    @Override
    public double getMaxHealth() {
        return stat.healthMax;
    }

    @Override
    public void heal(double amount) {
        setHealth(stat.healthCur + amount);
    }

    @Override
    public boolean isDead() {
        return stat.healthCur <= 0;
    }

    @Override
    public void kill() {
        stat.healthCur = 0;
        sidebar.modifyName("hp", CCDamage.FRM.BARHEALTH.parse(stat.healthCur));
        CCDamage.healthBoard.updatePlayer(this);
        player.damage(100);
    }

    @Override
    public void resetHealth() {
        stat.healthCur = stat.healthMax;
        updateHealth(false);
    }

    @Override
    public void setHealth(double amount) {
        boolean isDamage = false;
        if(stat.healthCur > amount) {
            isDamage = true;
        }
        stat.healthCur = amount;
        if(stat.healthCur > stat.healthMax) {
            resetHealth();
            return;
        } else if(isDead()) {
            kill();
            return;
        }
        updateHealth(isDamage);
    }

    @Override
    public void setLastDamaged(long time) {
        stat.setLastDamaged(time);
    }

    @Override
    public void setMaxHealth(double amount) {
        stat.healthMax = amount;
        if(stat.healthMax <= 0) {
            stat.healthMax = 1;
        }
        if(stat.healthCur > stat.healthMax) {
            resetHealth();
            return;
        }
        updateHealth(false);
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
        if(packet == null) {
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
        player.setOverheadName(TextHelper.shrink(name, false));
        return oldName;
    }

    public void setTabListName(String name) {
        player.setPlayerListName(TextHelper.shrink(name, true));
    }

    public void sendBlockChange(Location location, Material material) {
        player.sendBlockChange(location, material, (byte) 0);
    }

    /*
     * Event
     */

    /*
     * BullData
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
