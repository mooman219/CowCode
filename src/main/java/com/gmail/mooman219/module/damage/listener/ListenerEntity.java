package com.gmail.mooman219.module.damage.listener;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import com.gmail.mooman219.bull.CDLiving;
import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.CEventFactory;
import com.gmail.mooman219.frame.time.TimeHelper;
import com.gmail.mooman219.handler.config.store.ConfigGlobal;
import com.gmail.mooman219.module.damage.event.PlayerDamageByLivingEvent;
import com.gmail.mooman219.module.damage.event.PlayerDamageByPlayerEvent;
import com.gmail.mooman219.module.damage.event.PlayerDamageEvent;

public class ListenerEntity implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageEvent event){
        event.setCancelled(true);
        if(event.getEntityType() == EntityType.PLAYER) {
            CDPlayer player = CDPlayer.get((Player) event.getEntity());
            long time = TimeHelper.time();
            if(time - player.getLastDamaged() > ConfigGlobal.module.damage.damageDelay) {
                if(event instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent originalEvent = (EntityDamageByEntityEvent) event;
                    if(originalEvent.getDamager() instanceof Player) {
                        handlePlayerDamageByPlayer(player, CDPlayer.get((Player) originalEvent.getDamager()), originalEvent, event.getDamage());
                        return;
                    } else if(originalEvent.getDamager() instanceof LivingEntity) {
                        handlePlayerDamageByLiving(player, CDLiving.get((LivingEntity) originalEvent.getDamager()), originalEvent, event.getDamage());
                        return;
                    } else if(originalEvent.getDamager() instanceof Arrow) {
                        LivingEntity shooter = ((Arrow) originalEvent.getDamager()).getShooter();
                        if(shooter != null) {
                            if(shooter instanceof Player) {
                                handlePlayerDamageByPlayer(player, CDPlayer.get((Player) shooter), originalEvent, event.getDamage());
                                return;
                            } else if(shooter instanceof LivingEntity) {
                                handlePlayerDamageByLiving(player, CDLiving.get(shooter), originalEvent, event.getDamage());
                                return;
                            }
                        }
                    }
                }
                // If this method hasn't returned yet, call the default damage shit.
                handlePlayerDamage(player, event, event.getDamage());
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onHeal(EntityRegainHealthEvent event) {
        if(event.getEntityType() == EntityType.PLAYER) {
            CDPlayer player = CDPlayer.get((Player) event.getEntity());
            player.heal(event.getAmount());
            event.setCancelled(true);
        }
    }

    public void handlePlayerDamage(CDPlayer player, EntityDamageEvent event, double damage) {
        PlayerDamageEvent customEvent = CEventFactory.callPlayerDamageEvent(player, event, event.getDamage());
        if(!customEvent.isCancelled()) {
            player.damage(customEvent.getDamage());
        }
    }

    public void handlePlayerDamageByPlayer(CDPlayer player, CDPlayer damager, EntityDamageByEntityEvent event, double damage) {
        PlayerDamageByPlayerEvent customEvent = CEventFactory.callPlayerDamageByPlayerEvent(player, damager, event, event.getDamage());
        if(!customEvent.isCancelled()) {
            player.damage(customEvent.getDamage());
        }
    }

    public void handlePlayerDamageByLiving(CDPlayer player, CDLiving damager, EntityDamageByEntityEvent event, double damage) {
        PlayerDamageByLivingEvent customEvent = CEventFactory.callPlayerDamageByLivingEvent(player, damager, event, event.getDamage());
        if(!customEvent.isCancelled()) {
            player.damage(customEvent.getDamage());
        }
    }
}
