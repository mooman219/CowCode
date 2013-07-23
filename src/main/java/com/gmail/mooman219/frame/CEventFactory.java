package com.gmail.mooman219.frame;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.gmail.mooman219.bull.CDLiving;
import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.handler.task.event.TickSecondAsyncEvent;
import com.gmail.mooman219.handler.task.event.TickSecondSyncEvent;
import com.gmail.mooman219.module.damage.event.PlayerDamageByLivingEvent;
import com.gmail.mooman219.module.damage.event.PlayerDamageByPlayerEvent;
import com.gmail.mooman219.module.damage.event.PlayerDamageEvent;
import com.gmail.mooman219.module.damage.type.DamageType;
import com.gmail.mooman219.module.region.event.RegionChangeEvent;
import com.gmail.mooman219.module.region.store.BasicRegion;
import com.gmail.mooman219.module.service.event.DataCreateEvent;
import com.gmail.mooman219.module.service.event.DataRemovalEvent;
import com.gmail.mooman219.module.service.event.DataVerifyEvent;

public class CEventFactory {
    public static DataCreateEvent callDataCreateEvent(PlayerLoginEvent event, CDPlayer player) {
        return callEvent(new DataCreateEvent(event, player));
    }

    public static DataRemovalEvent callDataRemovalEvent(boolean async, CDPlayer player) {
        return callEvent(new DataRemovalEvent(async, player));
    }

    public static DataVerifyEvent callDataVerifyEvent(AsyncPlayerPreLoginEvent event, CDPlayer player) {
        return callEvent(new DataVerifyEvent(event, player));
    }

    public static RegionChangeEvent callRegionChangeEvent(PlayerMoveEvent event, CDPlayer player, BasicRegion oldRegion, BasicRegion newRegion) {
        return callEvent(new RegionChangeEvent(event, player, oldRegion, newRegion));
    }

    public static PlayerDamageEvent callPlayerDamageEvent(CDPlayer target, DamageType damageType, double damage) {
        return callEvent(new PlayerDamageEvent(target, damageType, damage));
    }

    public static PlayerDamageByPlayerEvent callPlayerDamageByPlayerEvent(CDPlayer target, CDPlayer damager, DamageType damageType, double damage) {
        return callEvent(new PlayerDamageByPlayerEvent(target, damager, damageType, damage));
    }

    public static PlayerDamageByLivingEvent callPlayerDamageByLivingEvent(CDPlayer target, CDLiving damager, DamageType damageType, double damage) {
        return callEvent(new PlayerDamageByLivingEvent(target, damager, damageType, damage));
    }

    public static TickSecondAsyncEvent callTickSecondAsyncEvent() {
        return callEvent(new TickSecondAsyncEvent());
    }

    public static TickSecondSyncEvent callTickSecondSyncEvent() {
        return callEvent(new TickSecondSyncEvent());
    }

    private static <T extends Event> T callEvent(T event) {
        Bukkit.getPluginManager().callEvent(event);
        return event;
    }
}
