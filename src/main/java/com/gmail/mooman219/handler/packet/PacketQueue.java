package com.gmail.mooman219.handler.packet;

import net.minecraft.server.Packet;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.gmail.mooman219.handler.task.type.CCLinkedBlockingQueue;

public class PacketQueue extends CCLinkedBlockingQueue<PacketWrapper>{
    public PacketQueue() {
        super(false, "CC PacketQueue");
    }

    public void put(Player target, Packet packet) {
        this.put(new PacketWrapper(target, packet));
    }

    @Override
    public Runnable getConsumer() {
        return new Runnable() {
            @Override
            public void run() {
                PacketWrapper packetQueueItem = take();
                if(packetQueueItem != null) {
                    if(packetQueueItem.target == null) {
                        for(Player player : Bukkit.getOnlinePlayers()) {
                            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packetQueueItem.packet);
                        }
                    } else {
                        if(((CraftPlayer)packetQueueItem.target).getHandle().playerConnection != null) {
                            ((CraftPlayer)packetQueueItem.target).getHandle().playerConnection.sendPacket(packetQueueItem.packet);
                        }
                    }
                }
            }
        };
    }
}
