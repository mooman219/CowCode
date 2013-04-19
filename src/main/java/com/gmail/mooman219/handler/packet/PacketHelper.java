package com.gmail.mooman219.handler.packet;

import net.minecraft.server.Packet;
import net.minecraft.server.Packet201PlayerInfo;
import net.minecraft.server.Packet206SetScoreboardObjective;
import net.minecraft.server.Packet207SetScoreboardScore;
import net.minecraft.server.Packet208SetScoreboardDisplayObjective;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.scoreboard.ScoreboardDisplayType;
import com.gmail.mooman219.frame.scoreboard.ScoreboardModifyType;

public class PacketHelper {
    public void sendSetScoreboardObjective(Player bukkitPlayer, String scoreboardTitle, String scoreboardDisplayTitle, ScoreboardModifyType scoreboardModifyType) {
        Packet206SetScoreboardObjective packet206 = new Packet206SetScoreboardObjective();
        packet206.a = scoreboardTitle;
        packet206.b = scoreboardDisplayTitle;
        packet206.c = scoreboardModifyType.id; // 0 Create - 1 Remove
        sendPacketPlayer(bukkitPlayer, packet206);
    }

    public void sendSetScoreboardScore(Player bukkitPlayer, String scoreboardTitle, String itemName, int itemValue, ScoreboardModifyType scoreboardModifyType) {
        Packet207SetScoreboardScore packet207 = new Packet207SetScoreboardScore();
        packet207.a = itemName;
        packet207.b = scoreboardTitle;
        packet207.c = itemValue;
        packet207.d = scoreboardModifyType.id; // 0 Create - 1 Remove
        sendPacketPlayer(bukkitPlayer, packet207);
    }

    public void sendSetScoreboardDisplay(Player bukkitPlayer, String scoreboardTitle, ScoreboardDisplayType scoreboardDisplayType) {
        Packet208SetScoreboardDisplayObjective packet208 = new Packet208SetScoreboardDisplayObjective();
        packet208.a = scoreboardDisplayType.id; // 0 List - 1 Sidebar - 2 belowName
        packet208.b = scoreboardTitle;
        sendPacketPlayer(bukkitPlayer, packet208);
    }


    public void sendPlayerInfo(Player bukkitPlayer, String name, boolean online, boolean ping) {
        Packet201PlayerInfo packet201 = new Packet201PlayerInfo(name, true, ping ? 0 : 40000);
        sendPacketPlayer(bukkitPlayer, packet201);
    }

    public void sendPacketAllPlayers(final Packet packet) {
        CHPacket.packetQueue.put(null, packet);
    }

    public void sendPacketPlayer(final Player bukkitPlayer, final Packet packet) {
        CHPacket.packetQueue.put(bukkitPlayer, packet);
    }

    // Old research

    /**
    public static void test(Player bukkitPlayer, String name, int particles) {
        Packet63WorldParticles packet63 = new Packet63WorldParticles();
        packet63.particlename = name;
        packet63.x = (float) bukkitPlayer.getLocation().getX();
        packet63.y = (float) bukkitPlayer.getLocation().getY() + 2;
        packet63.z = (float) bukkitPlayer.getLocation().getZ();
        packet63.offsetx = 0f;
        packet63.offsety = 0.2f;
        packet63.offsetz = 0f;
        packet63.particlespeed = 0.2f;
        packet63.totalparticles = particles;
        sendPacketAllPlayers(packet63);
    }

    public static void sendFootStep(Player bukkitPlayer) {
        Packet63WorldParticles packet63 = new Packet63WorldParticles();
        packet63.particlename = "footstep";
        packet63.x = (float) bukkitPlayer.getLocation().getX();
        packet63.y = (float) bukkitPlayer.getLocation().getY() + 0.005f;
        packet63.z = (float) bukkitPlayer.getLocation().getZ();
        packet63.offsetx = 0f;
        packet63.offsety = 0f;
        packet63.offsetz = 0f;
        packet63.particlespeed = 0f;
        packet63.totalparticles = 1;
        sendPacketAllPlayers(packet63);
    }
    /**/
}