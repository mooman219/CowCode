package com.gmail.mooman219.module.item.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import com.gmail.mooman219.bull.CDPlayer;

public class ButtonClickEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final CDPlayer clicker;
    private final ItemStack clickedItem;
    private final ItemStack heldItem;
    private final ClickType clickType;

    public ButtonClickEvent(CDPlayer clicker, ItemStack clickedItem, ItemStack heldItem, ClickType clickType) {
        this.clicker = clicker;
        this.clickedItem = clickedItem;
        this.heldItem = heldItem;
        this.clickType = clickType;
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

    public ItemStack getClickedItem() {
        return clickedItem;
    }

    public ClickType getClickType() {
        return clickType;
    }

    public ItemStack getHeldItem() {
        return heldItem;
    }
}
