package com.gmail.mooman219.frame;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class EventHelper {
    public static <T extends Event> T callEvent(T event) {
        Bukkit.getPluginManager().callEvent(event);
        return event;
    }
}
