package com.gmail.mooman219.frame;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_6_R2.entity.CraftEntity;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import net.minecraft.server.v1_6_R2.EntityPlayer;
import net.minecraft.server.v1_6_R2.Packet;
import net.minecraft.server.v1_6_R2.Packet17EntityLocationAction;
import net.minecraft.server.v1_6_R2.Packet18ArmAnimation;
import net.minecraft.server.v1_6_R2.Packet201PlayerInfo;
import net.minecraft.server.v1_6_R2.Packet206SetScoreboardObjective;
import net.minecraft.server.v1_6_R2.Packet207SetScoreboardScore;
import net.minecraft.server.v1_6_R2.Packet208SetScoreboardDisplayObjective;
import net.minecraft.server.v1_6_R2.Packet209SetScoreboardTeam;
import net.minecraft.server.v1_6_R2.Packet40EntityMetadata;
import net.minecraft.server.v1_6_R2.Packet8UpdateHealth;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.scoreboard.BoardDisplayType;
import com.gmail.mooman219.frame.scoreboard.BoardModifyType;
import com.gmail.mooman219.frame.team.TeamModifyType;
import com.gmail.mooman219.frame.text.TextHelper;

public class PacketHelper {
    public static Packet209SetScoreboardTeam getSetScoreboardTeam(TeamModifyType modifyType, boolean friendlyFire, boolean seeInvisable, String scoreboardName, String displayName, String prefix, String suffix, Collection<String> players) {
        Packet209SetScoreboardTeam packet209 = new Packet209SetScoreboardTeam();
        packet209.f = modifyType.getID();
        packet209.a = scoreboardName;
        if(modifyType == TeamModifyType.CREATED || modifyType == TeamModifyType.UPDATED) {
            int data = 0;
            if(friendlyFire) {
                data |= 1;
            }
            if(seeInvisable) {
                data |= 2;
            }
            packet209.b = displayName;
            packet209.c = prefix;
            packet209.d = suffix;
            packet209.g = data;
            if(modifyType == TeamModifyType.CREATED) {
                packet209.e = players;
            }
        }
        return packet209;
    }

    public static Packet17EntityLocationAction getEntityLocationAction(Entity entity, int data) {
        net.minecraft.server.v1_6_R2.Entity handle = ((CraftEntity)entity).getHandle();
        Packet17EntityLocationAction packet17 = new Packet17EntityLocationAction(handle, data, entity.getLocation().getBlockX(), entity.getLocation().getBlockY(), entity.getLocation().getBlockZ());
        return packet17;
    }

    public static Packet40EntityMetadata getEntityMetadata(Entity entity) {
        net.minecraft.server.v1_6_R2.Entity handle = ((CraftEntity)entity).getHandle();
        Packet40EntityMetadata packet40 = new Packet40EntityMetadata(handle.id, handle.getDataWatcher(), true);
        return packet40;
    }

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

    public static Packet18ArmAnimation getArmAnimation(EntityPlayer player, int id) {
        Packet18ArmAnimation packet18 = new Packet18ArmAnimation(player, id);
        return packet18;
    }

    public static Packet201PlayerInfo getPlayerInfo(String name, boolean online, boolean ping) {
        Packet201PlayerInfo packet201 = new Packet201PlayerInfo(name, online, ping ? 0 : 40000);
        return packet201;
    }

    // TODO - SUPER AWESOME FAKE DEATH ANIMATION SHIT
    public static Packet8UpdateHealth getUpdateHealth(float health, int foodlevel, float foodsaturation) {
        Packet8UpdateHealth packet8 = new Packet8UpdateHealth(health, foodlevel, foodsaturation);
        return packet8;
    }

    public static void sendWorld(World world, Packet... packets) {
        for(Player player : world.getPlayers()) {
            if(player == null) {
                continue;
            }
            send(CDPlayer.get(player), packets);
        }
    }

    public static void sendGlobal(Packet... packets) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player == null) {
                continue;
            }
            send(CDPlayer.get(player), packets);
        }
    }

    public static void sendGlobalExcept(CDPlayer player, Packet... packets) {
        for(Player other : Bukkit.getOnlinePlayers()) {
            if(other == null || other.getName().equals(player.getUsername())) {
                continue;
            }
            send(CDPlayer.get(other), packets);
        }
    }

    public static void sendNearby(Location location, Packet... packets) {
        sendNearby(location, 48, packets);
    }

    public static void sendNearby(Location location, double radius, Packet... packets) {
        radius *= radius;
        final org.bukkit.World world = location.getWorld();
        for(Player player : world.getPlayers()) {
            if(player == null) {
                continue;
            }
            if (location.distanceSquared(player.getLocation()) > radius) {
                continue;
            }
            send(CDPlayer.get(player), packets);
        }
    }

    public static void send(CDPlayer player, Packet... packets) {
        EntityPlayer handle = player.getHandle();
        if(handle == null) {
            Loader.warning("send(): Null handle for '" + player.getUsername() + "'");
        } else if(handle.playerConnection == null) {
            Loader.warning("send(): Null playerconnection for '" + player.getUsername() + "'");
        } else {
            for(Packet packet : packets) {
                if(packet != null) {
                    handle.playerConnection.sendPacket(packet);
                } else {
                    Loader.warning("sendPacket(): Null packet for '" + player.getUsername() + "'");
                }
            }
        }
    }
}
