package com.gmail.mooman219.module.chat.task;

import net.minecraft.server.PlayerConnection;

import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.gmail.mooman219.handler.task.type.CCTask;

public class ChatTask extends CCTask {
    public final Player sender;
    public final String message;

    public ChatTask(Player sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public static ChatTask get(Player sender, String message) {
        return new ChatTask(sender, message);
    }

    @Override
    public void run() {
        PlayerConnection target = ((CraftPlayer)sender).getHandle().playerConnection;
        if(target == null) {
            return;
        }
        target.chat(message, true);
    }
}