package com.gmail.mooman219.handler.packet;

import net.minecraft.server.Packet101CloseWindow;
import net.minecraft.server.Packet201PlayerInfo;
import net.minecraft.server.Packet206SetScoreboardObjective;
import net.minecraft.server.Packet207SetScoreboardScore;
import net.minecraft.server.Packet208SetScoreboardDisplayObjective;

import com.gmail.mooman219.bullbukkit.CDPlayer;
import com.gmail.mooman219.core.CowHandler;
import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.scoreboard.BoardDisplayType;
import com.gmail.mooman219.frame.scoreboard.BoardModifyType;

public class CHPacket implements CowHandler {
    public static String cast = "[CC][H][Packet] ";

    public static Manager manager;

    @Override
    public void onEnable() {
        manager = new Manager();
        Loader.info(cast + "Enabled");
    }

    @Override
    public void onDisable() {
        Loader.info(cast + "Disabled");
    }

    public class Manager {
        public void sendCloseWindow(CDPlayer player, int windowID) {
            Packet101CloseWindow packet101 = new Packet101CloseWindow(windowID);
            player.sendPacket(packet101);
        }

        public void sendSetScoreboardObjective(CDPlayer player, String scoreboardTitle, String scoreboardDisplayTitle, BoardModifyType scoreboardModifyType) {
            Packet206SetScoreboardObjective packet206 = new Packet206SetScoreboardObjective();
            packet206.a = scoreboardTitle;
            packet206.b = scoreboardDisplayTitle;
            packet206.c = scoreboardModifyType.id; // 0 Create - 1 Remove
            player.sendPacket(packet206);
        }

        public void sendSetScoreboardScore(CDPlayer player, String scoreboardTitle, String itemName, int itemValue, BoardModifyType scoreboardModifyType) {
            Packet207SetScoreboardScore packet207 = new Packet207SetScoreboardScore();
            packet207.a = itemName;
            packet207.b = scoreboardTitle;
            packet207.c = itemValue;
            packet207.d = scoreboardModifyType.id; // 0 Create - 1 Remove
            player.sendPacket(packet207);
        }

        public void sendSetScoreboardDisplay(CDPlayer player, String scoreboardTitle, BoardDisplayType scoreboardDisplayType) {
            Packet208SetScoreboardDisplayObjective packet208 = new Packet208SetScoreboardDisplayObjective();
            packet208.a = scoreboardDisplayType.id; // 0 List - 1 Sidebar - 2 belowName
            packet208.b = scoreboardTitle;
            player.sendPacket(packet208);
        }

        public void sendPlayerInfo(CDPlayer player, String name, boolean online, boolean ping) {
            Packet201PlayerInfo packet201 = new Packet201PlayerInfo(name, online, ping ? 0 : 40000);
            player.sendPacket(packet201);
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
}
