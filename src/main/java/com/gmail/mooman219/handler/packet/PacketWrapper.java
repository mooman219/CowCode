package com.gmail.mooman219.handler.packet;

import net.minecraft.server.Packet;

import org.bukkit.entity.Player;

public class PacketWrapper {
    public final Player target;
    public final Packet packet;

    public PacketWrapper(Player target, Packet packet) {
        this.target = target;
        this.packet = packet;
    }
}
