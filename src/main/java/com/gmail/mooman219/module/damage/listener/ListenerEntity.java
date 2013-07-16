package com.gmail.mooman219.module.damage.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import com.gmail.mooman219.bull.CDPlayer;

public class ListenerEntity implements Listener {
    @EventHandler()
    public void onDamage(EntityDamageEvent event){
        if(event.getEntityType() == EntityType.PLAYER) {
            CDPlayer player = CDPlayer.get((Player) event.getEntity());
            long time = System.currentTimeMillis();
            if(time - player.getLastDamaged() > 100) {
                player.damage(event.getDamage());
                player.setLastDamaged(time);
            }
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onHeal(EntityRegainHealthEvent event) {
        if(event.getEntityType() == EntityType.PLAYER) {
            CDPlayer player = CDPlayer.get((Player) event.getEntity());
            player.heal(event.getAmount());
            event.setAmount(0);
        }
    }
}
