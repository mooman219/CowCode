package com.gmail.mooman219.module.chat.queue;

import net.minecraft.server.PlayerConnection;

import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.gmail.mooman219.handler.task.type.CCLinkedBlockingQueue;

public class ChatQueue extends CCLinkedBlockingQueue<ChatQueueItem>{
    public ChatQueue() {
        super(false, "CC ChatQueue");
    }

    public void put(Player sender, String message) {
        this.put(new ChatQueueItem(sender, message));
    }

    public Runnable getConsumer() {
        return new Runnable() {
            public void run() {
                ChatQueueItem chatQueueItem = take();
                PlayerConnection target = ((CraftPlayer)chatQueueItem.sender).getHandle().playerConnection;
                if(target == null) {
                    return;
                }
                target.chat(chatQueueItem.message, true);
            }
        };
    }
}
