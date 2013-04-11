package com.gmail.mooman219.handler.packet;

import com.gmail.mooman219.core.Handler;
import com.gmail.mooman219.core.Loader;

public class CHPacket implements Handler {
    public static PacketQueue packetQueue;

    public static String cast = "[CC][H][Packet] ";

    public static PacketHelper helper;

    public void onEnable() {
        Loader.info(cast + "Creating PacketHelper");
        helper = new PacketHelper();
        Loader.info(cast + "Starting PacketQueue");
        packetQueue = new PacketQueue();
        packetQueue.start();
        Loader.info(cast + "Enabled");
    }

    public void onDisable() {
        Loader.info(cast + "Stopping PacketQueue");
        packetQueue.stop();
        Loader.info(cast + "Disabled");
    }
}
