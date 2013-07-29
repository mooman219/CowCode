package com.gmail.mooman219.frame.scoreboard;

import net.minecraft.server.v1_6_R2.Packet;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.PacketHelper;
import com.gmail.mooman219.frame.math.NumberHelper;

public class HealthBoard {
    private final String title;
    private final String displayTitle;

    public HealthBoard(String title, String displayTitle) {
        this.title = title;
        this.displayTitle = displayTitle;
    }

    public void addPlayer(CDPlayer player) {
        int health = NumberHelper.round(player.stat.healthCur);
        String name = player.getUsername();
        Packet packet = PacketHelper.getSetScoreboardScore(title, name, health, BoardModifyType.UPDATE);
        // Create the board on the client for the new player
        player.sendPacket(PacketHelper.getSetScoreboardObjective(title, displayTitle, BoardModifyType.UPDATE));
        player.sendPacket(PacketHelper.getSetScoreboardDisplay(title, BoardDisplayType.BELOWNAME));
        // Tell all players on the server the given player's name and health,
        // Also tell the given player the health of all other players
        PacketHelper.sendGlobalExcept(player, packet);
        for(Player bukkitOther : Bukkit.getOnlinePlayers()) {
            if(bukkitOther.getName().equals(player.getUsername())) {
                continue;
            }
            CDPlayer other = CDPlayer.get(bukkitOther);
            player.sendPacket(PacketHelper.getSetScoreboardScore(title, other.getUsername(), NumberHelper.round(other.stat.healthCur), BoardModifyType.UPDATE));
        }
    }

    public void updatePlayer(CDPlayer player) {
        int health = NumberHelper.round(player.stat.healthCur);
        String name = player.getUsername();
        Packet packet = PacketHelper.getSetScoreboardScore(title, name, health, BoardModifyType.UPDATE);
        // Tell all players on the server the given player's name and health
        PacketHelper.sendGlobalExcept(player, packet);
    }
}
