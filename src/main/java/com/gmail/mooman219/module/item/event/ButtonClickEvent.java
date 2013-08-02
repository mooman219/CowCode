package com.gmail.mooman219.module.item.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import com.gmail.mooman219.bull.CDPlayer;

public class ButtonClickEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final CDPlayer clicker;
    private final ItemStack item;

    public ButtonClickEvent(CDPlayer clicker, ItemStack item) {
        this.clicker = clicker;
        this.item = item;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public CDPlayer getClicker() {
        return clicker;
    }

    public ItemStack getItem() {
        return item;
    }
}
