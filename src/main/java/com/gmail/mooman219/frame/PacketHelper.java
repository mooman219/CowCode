package com.gmail.mooman219.frame;

import java.util.Arrays;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.minecraft.server.Packet;
import net.minecraft.server.Packet201PlayerInfo;
import net.minecraft.server.Packet205ClientCommand;
import net.minecraft.server.Packet206SetScoreboardObjective;
import net.minecraft.server.Packet207SetScoreboardScore;
import net.minecraft.server.Packet208SetScoreboardDisplayObjective;
import net.minecraft.server.Packet8UpdateHealth;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.scoreboard.BoardDisplayType;
import com.gmail.mooman219.frame.scoreboard.BoardModifyType;
import com.gmail.mooman219.frame.text.TextHelper;

public class PacketHelper {
    private static final Location PACKET_CACHE_LOCATION = new Location(null, 0, 0, 0);

    public static void sendSetScoreboardObjective(CDPlayer player, String scoreboardTitle, String scoreboardDisplayTitle, BoardModifyType scoreboardModifyType) {
        Packet206SetScoreboardObjective packet206 = new Packet206SetScoreboardObjective();
        packet206.a = scoreboardTitle;
        packet206.b = scoreboardDisplayTitle;
        packet206.c = scoreboardModifyType.getID(); // 0 Create - 1 Remove
        player.sendPacket(packet206);
    }

    public static void sendSetScoreboardScore(CDPlayer player, String scoreboardTitle, String itemName, int itemValue, BoardModifyType scoreboardModifyType) {
        Packet207SetScoreboardScore packet207 = new Packet207SetScoreboardScore();
        packet207.a = TextHelper.shrink(itemName);
        packet207.b = scoreboardTitle;
        packet207.c = itemValue;
        packet207.d = scoreboardModifyType.getID(); // 0 Create - 1 Remove
        player.sendPacket(packet207);
    }

    public static void sendSetScoreboardDisplay(CDPlayer player, String scoreboardTitle, BoardDisplayType scoreboardDisplayType) {
        Packet208SetScoreboardDisplayObjective packet208 = new Packet208SetScoreboardDisplayObjective();
        packet208.a = scoreboardDisplayType.getID(); // 0 List - 1 Sidebar - 2 belowName
        packet208.b = scoreboardTitle;
        player.sendPacket(packet208);
    }

    public static void sendPlayerInfo(CDPlayer player, String name, boolean online, boolean ping) {
        Packet201PlayerInfo packet201 = new Packet201PlayerInfo(name, online, ping ? 0 : 40000);
        player.sendPacket(packet201);
    }

    public static void sendUpdateHealth(CDPlayer player, float health, int foodlevel, float foodsaturation) {
        Packet8UpdateHealth packet8 = new Packet8UpdateHealth(health, foodlevel, foodsaturation);
        player.sendPacket(packet8);
    }

    public static void sendForceRespawn(CDPlayer player) {
        Packet205ClientCommand packet205 = new Packet205ClientCommand();
        packet205.a = 1;
        player.sendPacket(packet205);
    }

    public static void sendPacketNearby(Location location, Packet packet) {
        sendPacketsNearby(location, Arrays.asList(packet), 64);
    }

    public static void sendPacketNearby(Location location, Packet packet, double radius) {
        sendPacketsNearby(location, Arrays.asList(packet), radius);
    }

    public static void sendPacketsNearby(Location location, Collection<Packet> packets) {
        sendPacketsNearby(location, packets, 64);
    }

    public static void sendPacketsNearby(Location location, Collection<Packet> packets, double radius) {
        radius *= radius;
        final org.bukkit.World world = location.getWorld();
        for (Player ply : Bukkit.getServer().getOnlinePlayers()) {
            if (ply == null || world != ply.getWorld()) {
                continue;
            }
            if (location.distanceSquared(ply.getLocation(PACKET_CACHE_LOCATION)) > radius) {
                continue;
            }
            CDPlayer player = CDPlayer.get(ply);
            for (Packet packet : packets) {
                player.sendPacket(packet);
            }
        }
    }
}
