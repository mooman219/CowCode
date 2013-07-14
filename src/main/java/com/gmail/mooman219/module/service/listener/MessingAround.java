package com.gmail.mooman219.module.service.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffectType;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.ItemHelper;
import com.gmail.mooman219.frame.NumberHelper;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.handler.task.event.TickSecondSyncEvent;

public class MessingAround implements Listener {
    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        event.getProjectile().setVelocity(event.getProjectile().getVelocity().multiply(1.5));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        CDPlayer playerData = CDPlayer.get(event.getPlayer());
        playerData.getSidebar().addKey("po3", Chat.YELLOW +"»" + Chat.GOLD + " Stats", 10);
        playerData.getSidebar().addKey("mp", Chat.DARK_AQUA + "" + Chat.BOLD + "MP" + Chat.DARK_AQUA + " 1234567", 8);
        playerData.getSidebar().addKey("po2", Chat.YELLOW +"»" + Chat.GOLD + " Region", 7);
        playerData.getSidebar().addKey("po1", Chat.YELLOW +"»" + Chat.GOLD + " Other", 4);
        playerData.getSidebar().addKey("memory", "Memory: Init", 3);
        playerData.getSidebar().addKey("tps", "TPS: Init", 2);
        playerData.getSidebar().addKey("mob", "Mobs: Init", 1);

        playerData.getTab().set(0, 0, Chat.DARK_AQUA + "" + Chat.BOLD + "Welcome:");
        playerData.getTab().set(1, 0, Chat.GOLD + "" + Chat.BOLD + "Gold:");
        playerData.getTab().set(3, 0, "Online:");
        playerData.getTab().set(4, 0, Chat.DARK_GREEN + "" + Chat.BOLD + "C" + Chat.GOLD + "" + Chat.BOLD + "RPG");

        playerData.getTab().set(0, 1, "mooman219");
        playerData.getTab().set(1, 1, "3000");
        playerData.getTab().set(3, 1, "132/200");

        playerData.getTab().set(0, 2, Chat.GRAY + "" + Chat.BOLD + "========");
        playerData.getTab().set(1, 2, Chat.GRAY + "" + Chat.BOLD + "========");
        playerData.getTab().set(2, 2, Chat.GRAY + "" + Chat.BOLD + "========");
        playerData.getTab().set(3, 2, Chat.GRAY + "" + Chat.BOLD + "========");
        playerData.getTab().set(4, 2, Chat.GRAY + "" + Chat.BOLD + "========");

        playerData.getTab().set(0, 3, Chat.RED + "" + Chat.BOLD + "STR:" + Chat.RED + " 1");
        playerData.getTab().set(0, 4, Chat.GREEN + "" + Chat.BOLD + "DEX:" + Chat.GREEN + " 8");
        playerData.getTab().set(0, 5, Chat.PURPLE + "" + Chat.BOLD + "CHA:" + Chat.PURPLE + " 2");
        playerData.getTab().set(1, 3, Chat.YELLOW + "" + Chat.BOLD + "CON:" + Chat.YELLOW + " 6");
        playerData.getTab().set(1, 4, Chat.BLUE + "" + Chat.BOLD + "WIS:" + Chat.BLUE + " 3");
        playerData.getTab().set(1, 5, Chat.DARK_AQUA + "" + Chat.BOLD + "INT:" + Chat.DARK_AQUA + " 1");

        playerData.getTab().set(0, 17, Chat.GRAY + "" + Chat.BOLD + "========");
        playerData.getTab().set(1, 17, Chat.GRAY + "" + Chat.BOLD + "========");
        playerData.getTab().set(2, 17, Chat.GRAY + "" + Chat.BOLD + "========");
        playerData.getTab().set(3, 17, Chat.GRAY + "" + Chat.BOLD + "========");
        playerData.getTab().set(4, 17, Chat.GRAY + "" + Chat.BOLD + "========");

        playerData.getTab().set(0, 18, Chat.RED + "" + Chat.BOLD + "Health:");
        playerData.getTab().set(1, 18, Chat.DARK_AQUA + "" + Chat.BOLD + "Mana:");
        playerData.getTab().set(3, 18, Chat.GRAY + "" + Chat.BOLD + "Level:");
        playerData.getTab().set(4, 18, Chat.GRAY + "" + Chat.BOLD + "EXP:");

        playerData.getTab().set(0, 19, "100/100");
        playerData.getTab().set(1, 19, "120/200");
        playerData.getTab().set(3, 19, "3");
        playerData.getTab().set(4, 19, "3/10");

        playerData.getTab().update();

        event.getPlayer().removePotionEffect(PotionEffectType.JUMP);
        event.getPlayer().addPotionEffect(PotionEffectType.JUMP.createEffect(200000000, 1));
    }

    @EventHandler
    public void onSecond(TickSecondSyncEvent event){
        double memUsed = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L;
        //double tps = Math.min(20,  Math.round(MinecraftServer.currentTPS * 10D) / 10.0D);
        for(Player player : Bukkit.getOnlinePlayers()) {
            int mobs = player.getWorld().getEntities().size();
            CDPlayer.get(player).getSidebar().modifyName("memory", "Memory: " + Chat.GREEN + (int)memUsed);
            //CDPlayer.get(player).getSidebar().modifyName("tps", "TPS: " + Chat.GREEN + tps);
            CDPlayer.get(player).getSidebar().modifyName("mob", "Mobs: " + Chat.GREEN + mobs);
        }
    }

    @EventHandler()
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getInventory().getTitle().startsWith("TEST")) {
            CDPlayer player = CDPlayer.get((Player) event.getWhoClicked());
            Inventory inv = Bukkit.createInventory(player.getPlayer(), 9, "TEST - " + (NumberHelper.nextRandom().nextInt(100) + 1));
            inv.setItem(0, ItemHelper.setName(276, Chat.GOLD + inv.getTitle()));
            player.getPlayer().openInventory(inv);
            //event.setCancelled(true);
        }
    }

    // Old research

    /**
    @EventHandler()
    public void onJoin(PlayerJoinEvent event) {
        playerData.player.setAllowFlight(true);
    }

    @EventHandler()
    public void onSneak(PlayerToggleSneakEvent event) {
        PlayerData playerData = PlayerManager.getPlayerData(event.getPlayer());
        if(!playerData.service.hasLanded) {
            event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(0).setY(-1));
        }
    }

    @EventHandler()
    public void onFlight(PlayerToggleFlightEvent event) {
        if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            PlayerData playerData = PlayerManager.getPlayerData(event.getPlayer());
            event.setCancelled(true);
            if(playerData.service.hasLanded) {
                playerData.service.hasLanded = false;
                playerData.service.scoreboard.modifyKeyName("onground", "On Ground:" + Chat.RED + " No");
                event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().setY(0.0).multiply(0.44).setY(0.6));
                //event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(1.2).add(new Vector(0f,.2f,0f)));
                WorldHelper.playEffect(playerData.player.getLocation(), Effect.STEP_SOUND, 49);
            } else {
                event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(0).setY(-2));
            }
        }
    }

    @EventHandler(ignoreCancelled = false)
    public void onMove(PlayerMoveEvent event) {
        PlayerData playerData = PlayerManager.getPlayerData(event.getPlayer());
        double yVelocity = playerData.player.getVelocity().getY();
        playerData.service.scoreboard.modifyKeyName("yv", "YV: " + (int)(yVelocity * 100));
        if(!playerData.service.hasLanded) {
            if(yVelocity < 0 && yVelocity > -0.08f && isGroundBelow(playerData.player.getLocation().clone())) {
                playerData.service.scoreboard.modifyKeyName("onground", "On Ground:" + Chat.GREEN + " Yes");
                playerData.service.hasLanded = true;
            }
        }
    }

    public boolean isGroundBelow(Location origin) {
        Location loc = origin.clone();
        if(loc.getBlock().getTypeId() != 0 || loc.subtract(0, 1, 0).getBlock().getTypeId() != 0 || loc.subtract(0, 1, 0).getBlock().getTypeId() != 0) {
            return true;
        } else {
            loc = origin.clone();
            loc.setX((loc.getX() < loc.getBlockX() + 0.5d) ? loc.getBlockX() - 1 : loc.getBlockX());
            loc.setZ((loc.getZ() < loc.getBlockZ() + 0.5d) ? loc.getBlockZ() - 1 : loc.getBlockZ());
            return (loc.getBlock().getTypeId() != 0 || loc.subtract(0, 1, 0).getBlock().getTypeId() != 0 || loc.subtract(0, 1, 0).getBlock().getTypeId() != 0);
        }
    }
    /**/
}
