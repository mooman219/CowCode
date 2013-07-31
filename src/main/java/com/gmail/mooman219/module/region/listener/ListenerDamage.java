package com.gmail.mooman219.module.region.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.gmail.mooman219.module.damage.event.PlayerDamageByLivingEvent;
import com.gmail.mooman219.module.damage.event.PlayerDamageByPlayerEvent;
import com.gmail.mooman219.module.damage.event.PlayerDamageEvent;

public class ListenerDamage implements Listener{
    @EventHandler()
    public void onPlayerDamageByLiving(PlayerDamageByLivingEvent event){
        switch(event.getTarget().region().getRegion().getCombatType()) {
        case CHAOTIC:
        case CONTESTED:
            break;
        case SAFE:
        default:
            event.setCancelled(true);
            break;
        }
    }

    @EventHandler()
    public void onPlayerDamageByPlayer(PlayerDamageByPlayerEvent event){
        switch(event.getTarget().region().getRegion().getCombatType()) {
        case CHAOTIC:
            break;
        case CONTESTED:
        case SAFE:
        default:
            event.setCancelled(true);
            break;
        }
    }

    @EventHandler()
    public void onPlayerDamage(PlayerDamageEvent event){
        switch(event.getTarget().region().getRegion().getCombatType()) {
        case CHAOTIC:
        case CONTESTED:
            break;
        case SAFE:
        default:
            event.setCancelled(true);
            break;
        }
    }
}
