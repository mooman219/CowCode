package com.gmail.mooman219.module.service.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.gmail.mooman219.frame.event.TickSecondSyncEvent;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.handler.packet.CHPacket;
import com.gmail.mooman219.module.DLPlayer;

public class MessingAround implements Listener {
    @EventHandler
    public void onDamageScoreBoard(EntityDamageEvent event) {
        if(event.getEntity().getType() == EntityType.PLAYER) {
            DLPlayer.get(((Player) event.getEntity())).service.scoreboard.modifyKeyName("lastdamage", "LastDmg: " + Chat.RED + event.getDamage());
        }
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        event.getProjectile().setVelocity(event.getProjectile().getVelocity().multiply(1.5));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        DLPlayer playerData = DLPlayer.get(event.getPlayer());
        playerData.service.scoreboard.addKey("po3", Chat.YELLOW +"»" + Chat.GOLD + " Stats", 10);
        playerData.service.scoreboard.addKey("hp", Chat.RED + "" + Chat.BOLD + "HP" + Chat.RED + " 1234567", 9);
        playerData.service.scoreboard.addKey("mp", Chat.DARK_AQUA + "" + Chat.BOLD + "MP" + Chat.DARK_AQUA + " 1234567", 8);
        playerData.service.scoreboard.addKey("po2", Chat.YELLOW +"»" + Chat.GOLD + " Region", 7);
        playerData.service.scoreboard.addKey("po1", Chat.YELLOW +"»" + Chat.GOLD + " Other", 4);
        playerData.service.scoreboard.addKey("memory", "Memory: Init", 3);
        playerData.service.scoreboard.addKey("lastdamage", "LastDmg: Init", 1);

        CHPacket.helper.sendPlayerInfo(event.getPlayer(), "  Health", true, true);
        CHPacket.helper.sendPlayerInfo(event.getPlayer(), "1,234,567", true, true);
        CHPacket.helper.sendPlayerInfo(event.getPlayer(), "*       8", true, true);
        CHPacket.helper.sendPlayerInfo(event.getPlayer(), "*             14", true, true);

        CHPacket.helper.sendPlayerInfo(event.getPlayer(), "1,234,567", true, false);
        CHPacket.helper.sendPlayerInfo(event.getPlayer(), " 2", true, false);
        CHPacket.helper.sendPlayerInfo(event.getPlayer(), "       8", true, false);
        CHPacket.helper.sendPlayerInfo(event.getPlayer(), "             14", true, false);
    }

    @EventHandler
    public void onSecond(TickSecondSyncEvent event){
        double memUsed = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L;
        for(Player player : Bukkit.getOnlinePlayers()) {
            DLPlayer playerData = DLPlayer.get(player);
            playerData.service.scoreboard.modifyKeyName("memory", "Memory: " + Chat.GREEN + (int)memUsed);
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
