package com.gmail.mooman219.bull;

import java.util.ArrayList;

import net.minecraft.server.v1_6_R2.PendingConnection;
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
import com.gmail.mooman219.module.chat.CCChat;
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
    private String prefix = "";
    private String suffix = "";
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
     * Called in PlayerPreLoginEvent.
     * This is done in another thread.
     */
    public void processPreLogin() {
        for(PlayerData playerdata : playerData) {
            playerdata.create();
        }
    }

    /**
     * Called in PlayerLoginEvent.
     */
    public void processLogin(Player player) {
        this.player = player;
    }

    /**
     * Called in PlayerJoinEvent.
     */
    public void processJoin() {
        sidebar = new Board(this, username, prefix + username + suffix, BoardDisplayType.SIDEBAR);
        tabList = new Tab(this);
    }

    /**
     * Called in PlayerQuitEvent.
     */
    public void processQuit() {
        sidebar = null;
        tabList = null;
        player = null;
        prefix = null;
        suffix = null;
    }

    /**
     * Called from the UploadRequest.
     * This is done in another thread.
     */
    public void processRemoval() {
        for(PlayerData data : playerData) {
            data.destroy();
        }
        playerData.clear();
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
    private float lastMoveSpeed = -10f;

    @Override
    public void damage(double amount) {
        if(!isDead()) {
            player.damage(0d);
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
            player.setHealth(0d);
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
        CHTask.getManager().runPlugin(new Runnable() {
            @Override
            public void run() {
                EntityPlayer handle = getHandle();
                if(handle == null) {
                    Loader.warning("chat(): Null handle for '" + username + "'");
                } else if(handle.playerConnection == null) {
                    Loader.warning("chat(): Null connection for '" + username + "'");
                } else {
                    handle.playerConnection.chat(message, true);
                }
            }
        });
    }

    public float getMoveSpeed() {
        return player.getWalkSpeed();
    }

    public String getOverheadPrefix() {
        return prefix;
    }

    public String getOverheadSuffix() {
        return suffix;
    }

    /**
     * This can return null depending on when it's called.
     */
    public Player getPlayer() {
        return player;
    }

    public Board getSidebar() {
        return sidebar;
    }

    public Tab getTab() {
        return tabList;
    }

    public String getUsername() {
        return username;
    }

    public void playAnimation(PlayerAnimation animation) {
        animation.play(player);
    }

    public void playAnimation(PlayerAnimation animation, int radius) {
        animation.play(player, radius);
    }

    public void sendBlockChange(Location location, Material material) {
        player.sendBlockChange(location, material, (byte) 0);
    }

    public void setDisplayName(String name) {
        player.setDisplayName(name + Chat.RESET);
    }

    /**
     * Recommended value is between -1 and 1
     */
    public void setMoveSpeed(float value) {
        player.setWalkSpeed(value);
    }

    public void setOverheadPrefix(String prefix) {
        this.prefix = TextHelper.shrink(prefix, false);
        if(sidebar != null && getHandle().playerConnection != null) {
            sidebar.modifyTitle(this.prefix + username + this.suffix);
        }
        CCChat.loneBoard.update(this);
    }

    public void setOverheadSuffix(String suffix) {
        this.suffix = TextHelper.shrink(suffix, false);
        if(sidebar != null && getHandle().playerConnection != null) {
            sidebar.modifyTitle(this.prefix + username + this.suffix);
        }
        CCChat.loneBoard.update(this);
    }

    public void setTabListName(String name) {
        player.setPlayerListName(TextHelper.shrink(name, true));
    }

    /*
     * Event
     */

    /*
     * BullData
     */

    public EntityPlayer getHandle() {
        return player != null ? ((CraftPlayer)player).getHandle() : null;
    }

    public static CDPlayer getSafe(Player player) {
        if(player == null) {
            return null;
        }
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
