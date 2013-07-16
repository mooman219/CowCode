package com.gmail.mooman219.frame;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import net.minecraft.server.Packet;
import net.minecraft.server.Packet201PlayerInfo;
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

    public static Packet206SetScoreboardObjective getSetScoreboardObjective(String scoreboardTitle, String scoreboardDisplayTitle, BoardModifyType scoreboardModifyType) {
        Packet206SetScoreboardObjective packet206 = new Packet206SetScoreboardObjective();
        packet206.a = scoreboardTitle;
        packet206.b = scoreboardDisplayTitle;
        packet206.c = scoreboardModifyType.getID(); // 0 Create - 1 Remove
        return packet206;
    }

    public static Packet207SetScoreboardScore getSetScoreboardScore(String scoreboardTitle, String itemName, int itemValue, BoardModifyType scoreboardModifyType) {
        Packet207SetScoreboardScore packet207 = new Packet207SetScoreboardScore();
        packet207.a = TextHelper.shrink(itemName, false);
        packet207.b = scoreboardTitle;
        packet207.c = itemValue;
        packet207.d = scoreboardModifyType.getID(); // 0 Create - 1 Remove
        return packet207;
    }

    public static Packet208SetScoreboardDisplayObjective getSetScoreboardDisplay(String scoreboardTitle, BoardDisplayType scoreboardDisplayType) {
        Packet208SetScoreboardDisplayObjective packet208 = new Packet208SetScoreboardDisplayObjective();
        packet208.a = scoreboardDisplayType.getID(); // 0 List - 1 Sidebar - 2 belowName
        packet208.b = scoreboardTitle;
        return packet208;
    }

    public static Packet201PlayerInfo getPlayerInfo(String name, boolean online, boolean ping) {
        Packet201PlayerInfo packet201 = new Packet201PlayerInfo(name, online, ping ? 0 : 40000);
        return packet201;
    }

    public static Packet8UpdateHealth getUpdateHealth(float health, int foodlevel, float foodsaturation) {
        Packet8UpdateHealth packet8 = new Packet8UpdateHealth(health, foodlevel, foodsaturation);
        return packet8;
    }

    public static void sendWorld(World world, Packet... packets) {
        for(Player player : world.getPlayers()) {
            if(player == null) {
                continue;
            }
            CDPlayer cdplayer = CDPlayer.get(player);
            for (Packet packet : packets) {
                cdplayer.sendPacket(packet);
            }
        }
    }

    public static void sendGlobal(Packet... packets) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player == null) {
                continue;
            }
            CDPlayer cdplayer = CDPlayer.get(player);
            for (Packet packet : packets) {
                cdplayer.sendPacket(packet);
            }
        }
    }

    public static void sendNearby(Location location, Packet... packets) {
        sendNearby(location, 64, packets);
    }

    public static void sendNearby(Location location, double radius, Packet... packets) {
        radius *= radius;
        final org.bukkit.World world = location.getWorld();
        for(Player player : world.getPlayers()) {
            if(player == null) {
                continue;
            }
            if (location.distanceSquared(player.getLocation(PACKET_CACHE_LOCATION)) > radius) {
                continue;
            }
            CDPlayer cdplayer = CDPlayer.get(player);
            for (Packet packet : packets) {
                cdplayer.sendPacket(packet);
            }
        }
    }
}
