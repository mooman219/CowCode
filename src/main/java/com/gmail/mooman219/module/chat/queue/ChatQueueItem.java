package com.gmail.mooman219.module.chat.queue;

import org.bukkit.entity.Player;

public class ChatQueueItem {
    public final Player sender;
    public final String message;

    public ChatQueueItem(Player sender, String message) {
        this.sender = sender;
        this.message = message;
    }
}
