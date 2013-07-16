package com.gmail.mooman219.frame.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.NumberHelper;
import com.gmail.mooman219.frame.PacketHelper;

/**
 * The faster version (this one), basically just doesn't save any information and
 * relies on the client to store all the names.
 */
public class HealthBoard {
    private final String title;
    private final String displayTitle;

    public HealthBoard(String title, String displayTitle) {
        this.title = title;
        this.displayTitle = displayTitle;
    }

    public void addPlayer(CDPlayer player) {
        int health = NumberHelper.floor(player.stat.healthCur);
        String name = player.getOverheadName();
        // Create the board on the client for the new player
        player.sendPacket(PacketHelper.getSetScoreboardObjective(title, displayTitle, BoardModifyType.UPDATE));
        player.sendPacket(PacketHelper.getSetScoreboardDisplay(title, BoardDisplayType.BELOWNAME));
        // Tell all players on the server the given player's name and health,
        // Also tell the given player the health of all other players
        for(Player bukkitOther : Bukkit.getOnlinePlayers()) {
            if(bukkitOther.getName().equals(player.getUsername())) {
                continue;
            }
            CDPlayer other = CDPlayer.get(bukkitOther);
            player.sendPacket(PacketHelper.getSetScoreboardScore(title, other.getOverheadName(), 2, BoardModifyType.UPDATE));
            other.sendPacket(PacketHelper.getSetScoreboardScore(title, name, health, BoardModifyType.UPDATE));
        }
    }

    public void updatePlayer(CDPlayer player) {
        int health = NumberHelper.floor(player.stat.healthCur);
        String name = player.getOverheadName();
        // Tell all players on the server the given player's name and health
        for(Player bukkitOther : Bukkit.getOnlinePlayers()) {
            if(bukkitOther.getName().equals(player.getUsername())) {
                continue;
            }
            CDPlayer other = CDPlayer.get(bukkitOther);
            other.sendPacket(PacketHelper.getSetScoreboardScore(title, name, health, BoardModifyType.UPDATE));
        }
    }
}
