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
        int health = NumberHelper.toInt(player.getPlayer().getHealth());
        String name = player.getOverheadName();
        // Create the board on the client for the new player
        PacketHelper.sendSetScoreboardObjective(player, title, displayTitle, BoardModifyType.UPDATE);
        PacketHelper.sendSetScoreboardDisplay(player, title, BoardDisplayType.BELOWNAME);
        // Tell all players on the server the given player's name and health,
        // Also tell the given player the health of all other players
        for(Player bukkitOther : Bukkit.getOnlinePlayers()) {
            if(bukkitOther.getName().equals(player.getUsername())) {
                continue;
            }
            CDPlayer other = CDPlayer.get(bukkitOther);
            PacketHelper.sendSetScoreboardScore(player, title, other.getOverheadName(), NumberHelper.ceil(player.stat.healthCur), BoardModifyType.UPDATE);
            PacketHelper.sendSetScoreboardScore(other, title, name, health, BoardModifyType.UPDATE);
        }
    }

    public void updatePlayer(CDPlayer player) {
        int health = NumberHelper.ceil(player.stat.healthCur);
        String name = player.getOverheadName();
        // Tell all players on the server the given player's name and health
        for(Player bukkitOther : Bukkit.getOnlinePlayers()) {
            if(bukkitOther.getName().equals(player.getUsername())) {
                continue;
            }
            CDPlayer other = CDPlayer.get(bukkitOther);
            PacketHelper.sendSetScoreboardScore(other, title, name, health, BoardModifyType.UPDATE);
        }
    }
}
