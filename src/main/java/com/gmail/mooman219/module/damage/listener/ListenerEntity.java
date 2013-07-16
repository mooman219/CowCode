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
    public void onDamage(EntityDamageEvent e){
        if(e.getEntityType() == EntityType.PLAYER) {
            CDPlayer player = CDPlayer.get((Player) e.getEntity());
            long time = System.currentTimeMillis();
            if(time - player.getLastDamaged() > 100) {
                player.damage(e.getDamage());
                player.setLastDamaged(time);
            }
            e.setCancelled(true);
        }
    }

    @EventHandler()
    public void onHeal(EntityRegainHealthEvent e) {
        if(e.getEntityType() == EntityType.PLAYER) {
            CDPlayer player = CDPlayer.get((Player) e.getEntity());
            player.heal(e.getAmount());
            e.setAmount(0);
        }
    }
}
