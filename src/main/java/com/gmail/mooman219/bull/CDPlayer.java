package com.gmail.mooman219.bull;

import java.util.ArrayList;

import net.minecraft.server.v1_6_R2.Packet;
import net.minecraft.server.v1_6_R2.PendingConnection;
import net.minecraft.server.v1_6_R2.PlayerConnection;
import net.minecraft.server.v1_6_R2.EntityPlayer;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_6_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.craftbukkit.BullData;
import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.database.MongoHelper;
import com.gmail.mooman219.frame.math.NumberHelper;
import com.gmail.mooman219.frame.scoreboard.Board;
import com.gmail.mooman219.frame.scoreboard.BoardDisplayType;
import com.gmail.mooman219.frame.tab.Tab;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.frame.time.TimeHelper;
import com.gmail.mooman219.handler.database.type.DownloadReason;
import com.gmail.mooman219.handler.database.type.UploadReason;
import com.gmail.mooman219.handler.task.CHTask;
import com.gmail.mooman219.layout.Damageable;
import com.gmail.mooman219.layout.PlayerData;
import com.gmail.mooman219.module.chat.store.PDChat;
import com.gmail.mooman219.module.damage.CCDamage;
import com.gmail.mooman219.module.item.store.PDItem;
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
    public PDItem item = null;

    public CDPlayer(String username) {
        this.username = username;
        // Create data
        this.playerData = new ArrayList<PlayerData>();
        this.service = addPlayerData(new PDService(this));
        this.login = addPlayerData(new PDLogin(this));
        this.chat = addPlayerData(new PDChat(this));
        this.stat = addPlayerData(new PDStat(this));
        this.region = addPlayerData(new PDRegion(this));
        this.item = addPlayerData(new PDItem(this));
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
            player = null;
            break;
        case POST_REMOVAL: // This is done in another thread
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
    public boolean sync(DownloadReason reason, DBObject playerObject) {
        for(PlayerData data : playerData) {
            try {
                data.sync(reason, MongoHelper.getValue(playerObject, data.getTag(), new BasicDBObject()));
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
     * This will make the health number update on the players screen.
     * This WILL NOT kill a player.
     */
    public void updateHealth() {
        if(!isDead()) {
            double percent = stat.healthCur / stat.healthMax;
            double health = percent * 20D; health = health <= 0 ? 1 : health;
            updateJump(percent);
            updateMoveSpeed(percent);
            player.setHealth(health);
        }
        tabList.set(0, 19, NumberHelper.toInt(stat.healthCur) + "/" + NumberHelper.toInt(stat.healthMax));
        tabList.update();
        sidebar.modifyName("hp", CCDamage.FRM.BARHEALTH.parse(stat.healthCur));
        CCDamage.healthBoard.updatePlayer(this);
    }

    /**
     * Used the percent to update the jump modifier applied on players.
     * The lower your health, the lower you can jump.
     */
    public void updateJump(double percent) {
        int modifier;
        if(percent > 0.5D) { // 100% - 50%
            modifier = 1;
        } else if(percent > 0.25D) { // 50% - 25%
            modifier = 0;
        } else { // 25% - 00%
            modifier = -1;
        }
        player.removePotionEffect(PotionEffectType.JUMP);
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200000000, modifier, true));
        getHandle().updateEffects = false;
    }

    /**
     * Used the percent to update the movespeed of players.
     * The lower your health, the slower you move
     *
     *  public float flySpeed = 0.05F;
     *  public float walkSpeed = 0.1F;
     */
    public void updateMoveSpeed(double percent) {
        float moveSpeed = 0.2f;
        if(percent > 0.5D) { // 100% - 50%
            moveSpeed *= 1.2f;
        } else if(percent > 0.25D) { // 50% - 25%
            moveSpeed *= 1.0f;
        } else { // 25% - 00%
            moveSpeed *= 0.8f;
        }
        if(lastMoveSpeed == moveSpeed) {
            return;
        }
        lastMoveSpeed = moveSpeed;
        player.setWalkSpeed(moveSpeed);
    }
    private float lastMoveSpeed = -10f; // Cached value

    @Override
    public void damage(double amount) {
        if(!isDead()) {
            player.damage(0);
            WorldHelper.playEffect(player.getEyeLocation(), Effect.STEP_SOUND, Material.REDSTONE_WIRE.getId());
        }
        setHealth(stat.healthCur - amount);
        setLastDamaged(TimeHelper.time());
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
        if(!isDead()) {
            WorldHelper.playEffect(player.getEyeLocation(), Effect.STEP_SOUND, Material.EMERALD_BLOCK.getId());
        }
        setHealth(stat.healthCur + amount);
    }

    @Override
    public boolean isDead() {
        return stat.healthCur <= 0 || player.isDead();
    }

    @Override
    public boolean isOverflowing() {
        return stat.healthCur > stat.healthMax;
    }

    @Override
    public void kill() {
        if(!player.isDead()) {
            player.setHealth(0);
        }
        stat.healthCur = 0;
        updateHealth();
    }

    @Override
    public void resetHealth() {
        stat.healthCur = stat.healthMax;
        updateHealth();
    }

    @Override
    public void setHealth(double amount) {
        stat.healthCur = amount;
        // Normal health overflow check
        if(isOverflowing()) {
            resetHealth();
        } else if(isDead()) {
            kill();
        } else {
            updateHealth();
        }
    }

    @Override
    public void setLastDamaged(long time) {
        stat.setLastDamaged(time);
    }

    @Override
    public void setMaxHealth(double amount) {
        stat.healthMax = amount;
        // Max health shouldn't be 0 EVER
        if(stat.healthMax <= 0) {
            stat.healthMax = 1;
        }
        setHealth(stat.healthCur);
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
        if(player == null) {
            throw new IllegalStateException("You cannot get a null player for " + username + ". CDPlayer might be shutdown or not started yet.");
        }
        return player;
    }

    public Board getSidebar() {
        return sidebar;
    }

    public Tab getTab() {
        return tabList;
    }

    public void playAnimation(PlayerAnimation animation) {
        animation.play(player);
    }

    public void playAnimation(PlayerAnimation animation, int radius) {
        animation.play(player, radius);
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

    /**
     * Recommended value is between -1 and 1
     */
    public void setMoveSpeed(float value) {
        player.setWalkSpeed(value);
    }

    public float getMoveSpeed() {
        return player.getWalkSpeed();
    }

    /*
     * Event
     */

    /*
     * BullData
     */

    public EntityPlayer getHandle() {
        if(player == null) {
            Loader.warning("Null player for '" + username + "'");
            return null;
        }
        return ((CraftPlayer)player).getHandle();
    }

    public static CDPlayer getSafe(Player player) {
        EntityPlayer handle = ((CraftPlayer)player).getHandle();
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
