package com.gmail.mooman219.bull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.minecraft.server.Container;
import net.minecraft.server.ContainerChest;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet100OpenWindow;
import net.minecraft.server.Packet101CloseWindow;
import net.minecraft.server.Packet20NamedEntitySpawn;
import net.minecraft.server.Packet29DestroyEntity;
import net.minecraft.server.Packet8UpdateHealth;
import net.minecraft.server.PendingConnection;
import net.minecraft.server.PlayerConnection;
import net.minecraft.server.PlayerInventory;

import org.bson.types.ObjectId;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.craftbukkit.inventory.CraftContainer;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.inventory.Inventory;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.craftbukkit.BullData;
import com.gmail.mooman219.frame.MongoHelper;
import com.gmail.mooman219.frame.scoreboard.Board;
import com.gmail.mooman219.frame.scoreboard.BoardDisplayType;
import com.gmail.mooman219.frame.scoreboard.HealthBoard;
import com.gmail.mooman219.frame.tab.Tab;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.handler.config.ConfigGlobal;
import com.gmail.mooman219.handler.database.UploadReason;
import com.gmail.mooman219.module.chat.store.PDChat;
import com.gmail.mooman219.module.chat.store.PLChat;
import com.gmail.mooman219.module.login.store.PDLogin;
import com.gmail.mooman219.module.rpg.stat.store.PDStat;
import com.gmail.mooman219.module.service.CCService;
import com.gmail.mooman219.module.service.store.PDService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class CDPlayer extends BullData {
    // ▀▀▀▀▀▀▀▀▀▀ Idea for mob health bar
    public final static HealthBoard healthBoard = new HealthBoard("health", Chat.RED + "" + Chat.BOLD + "HP"); // The slow one makes the client run faster
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

    public boolean isInventoryOpen() {
        EntityPlayer handle = getHandle();
        return handle.activeContainer != handle.defaultContainer;
    }
    
    /**
     * THIS DROPS AN ITEM A PLAYER IS HOLDING WHILE IN AN INVENTORY
     */
    public void dropItemInHand() {
        EntityPlayer handle = getHandle();
        PlayerInventory playerinventory = handle.inventory;
        if (playerinventory.getCarried() != null) {
            handle.drop(playerinventory.getCarried());
            playerinventory.setCarried((ItemStack) null);
        }
    }

    /**
 +----  closeInventory() {
 |        CraftEventFactory.handleInventoryCloseEvent(this); // CraftBukkit
 |        this.playerConnection.sendPacket(new Packet101CloseWindow(this.activeContainer.windowId));
 |   +--  j() {
 |   |   +  b(EntityHuman entityhuman) {
 |   |   |    
 |   |   |    PlayerInventory playerinventory = entityhuman.inventory;
 |   |   |    if (playerinventory.getCarried() != null) {
 |   |   |        entityhuman.drop(playerinventory.getCarried());
 |   |   |        playerinventory.setCarried((ItemStack) null);
 |   |   |    }
 |   |   +  }
 |   |      this.activeContainer = this.defaultContainer;
 |   +--  }
 +----  }
     */
    public void closeInventory() {
        EntityPlayer handle = getHandle();
        sendPacket(new Packet101CloseWindow(handle.activeContainer.windowId)); // Tell the player to close their inventory
        handle.activeContainer.transferTo(handle.defaultContainer, (CraftHumanEntity) player); // Tell bukkit to close the inventory
        dropItemInHand();
        handle.activeContainer = handle.defaultContainer; // Close the inventory
    }

    /**
 +----  openInventory(Inventory inventory) {
 |        if(!(getHandle() instanceof EntityPlayer)) return null;
 |        EntityPlayer player = (EntityPlayer) getHandle();
 |        InventoryType type = inventory.getType();
 |        Container formerContainer = getHandle().activeContainer;
 |        // TODO: Should we check that it really IS a CraftInventory first?
 |        CraftInventory craftinv = (CraftInventory) inventory;        
 |   +--  openContainer(IInventory iinventory) { [ getHandle().openContainer(craftinv.getInventory()); ]
 |   |      if (this.activeContainer != this.defaultContainer) {
 |   |          this.closeInventory();
 |   |      }
 |   |      
 |   |      // CraftBukkit start - Inventory open hook
 |   |      Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerChest(this.inventory, iinventory));
 |   |      if(container == null) return;
 |   |      // CraftBukkit end
 |   |      
 |   |      this.nextContainerCounter();
 |   |      this.playerConnection.sendPacket(new Packet100OpenWindow(this.containerCounter, 0, iinventory.getName(), iinventory.getSize(), iinventory.c()));
 |   |      this.activeContainer = container; // CraftBukkit - Use container we passed to event
 |   |      this.activeContainer.windowId = this.containerCounter;
 |   |      this.activeContainer.addSlotListener(this);
 |   +--  }
 |        getHandle().openContainer(craftinv.getInventory());
 |        if (getHandle().activeContainer == formerContainer) {
 |            return null;
 |        }
 |        getHandle().activeContainer.checkReachable = false;
 |        return getHandle().activeContainer.getBukkitView();
 +----  }
     */
    public void openInventory(Inventory inventory) {
        if(isInventoryOpen()) {
            EntityPlayer handle = getHandle();
            CraftPlayer craftPlayer = (CraftPlayer) player;
            CraftInventory craftInventory = (CraftInventory) inventory;
            IInventory iinventory = craftInventory.getInventory();
            Container container = new ContainerChest(handle.inventory, iinventory);
            container.checkReachable = false;
            //
            dropItemInHand();
            sendPacket(new Packet101CloseWindow(handle.activeContainer.windowId));
            //
            handle.activeContainer.transferTo(container, craftPlayer);
            
            int counter = handle.nextContainerCounter();
            int windowType = CraftContainer.getNotchInventoryType(inventory.getType());
            String title = iinventory.getName();
            int size = iinventory.getSize();
            boolean useTitle = true;
            
            handle.playerConnection.sendPacket(new Packet100OpenWindow(counter, windowType, title, size, useTitle));
            handle.activeContainer = container;
            handle.activeContainer.windowId = counter;
            handle.activeContainer.addSlotListener(handle);
        } else {
            player.openInventory(inventory);
        }
    }

    public String getName() {
        return username;
    }

    public String getOverheadName() {
        return getHandle().overheadName;
    }

    public Player getPlayer() {
        if(player == null) {
            throw new IllegalStateException("Null player");
        }
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
        EntityPlayer handle = getHandle();
        String oldName = handle.overheadName;
        if(name == null || name.equals(oldName)) {
            return oldName;
        }
        handle.overheadName = name;
        if(handle.playerConnection != null) {
            sidebar.modifyTitle(name);
            for(Player other : player.getWorld().getPlayers()) {
                if(other.getEntityId() == player.getEntityId() || other.getLocation().distanceSquared(player.getLocation()) > ConfigGlobal.nameUpdateRadius) { // 240 Blocks // 15 Chunks
                    continue;
                }
                CDPlayer otherPlayer = get(other);
                otherPlayer.sendPacket(new Packet29DestroyEntity(handle.id));
                otherPlayer.sendPacket(new Packet20NamedEntitySpawn(handle));
            }
            healthBoard.updatePlayer(this);
        }
        return oldName;
    }

    // Updates the current player's (health, foodlevel, foodsaturation)
    // Health max = 20, FoodLevel max = 20, FoodSaturation max = 5f
    public void updateStatus(int health, int foodlevel, float foodsaturation) {
        sendPacket(new Packet8UpdateHealth(health, foodlevel, foodsaturation));
    }

    public void setTabListName(String name) {
        player.setPlayerListName(TextHelper.shrink(name));
    }

    public int getArrowsStuck() {
        return getHandle().bM();
    }

    public void setArrowsStuck(int arrows) {
        getHandle().r(arrows);
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

    public static CDPlayer get(EntityPlayer player) {
        return get(player.getBukkitEntity());
    }

    public static void set(AsyncPlayerPreLoginEvent event, CDPlayer player) {
        if(event.getPendingConnection() != null) {
            ((PendingConnection) event.getPendingConnection()).bull_live = player;
        } else {
            throw new IllegalArgumentException("Unable to bind CDPlayer to login for '" + player.getName() + "'.");
        }
    }
}
