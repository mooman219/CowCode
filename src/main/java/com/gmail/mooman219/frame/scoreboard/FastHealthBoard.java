package com.gmail.mooman219.frame.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bullbukkit.CDPlayer;
import com.gmail.mooman219.handler.packet.CHPacket;
import com.gmail.mooman219.handler.task.CHTask;

/**
 * The faster version (this one), basically just doesn't save any information and
 * relies on the client to store all the names.  The slower version keeps track of
 * all the names on the board and removes unused ones.  The only downside to the
 * faster version is that a client that has been playing for a few hours might
 * have a shit ton of names stored locally.
 */
public class FastHealthBoard {
    private final String title;
    private final String displayTitle;

    public FastHealthBoard(final String title, final String displayTitle) {
        this.title = title;
        this.displayTitle = displayTitle;
    }

    public void addPlayer(final CDPlayer player) {
        final int health = player.getPlayer().getHealth();
        final String name = player.getOverheadName();
        CHTask.manager.runOrdered(new Runnable() {
            @Override
            public void run() {
                // Create the board on the client for the new player
                CHPacket.manager.sendSetScoreboardObjective(player, title, displayTitle, BoardModifyType.UPDATE);
                CHPacket.manager.sendSetScoreboardDisplay(player, title, BoardDisplayType.BELOWNAME);
                // Tell all players on the server the given player's name and health,
                // Also tell the given player the health of all other players
                for(Player bukkitOther : Bukkit.getOnlinePlayers()) {
                    if(bukkitOther.getName().equals(player.getName())) {
                        continue;
                    }
                    CDPlayer other = CDPlayer.get(bukkitOther);
                    CHPacket.manager.sendSetScoreboardScore(player, title, other.getOverheadName(), bukkitOther.getHealth(), BoardModifyType.UPDATE);
                    CHPacket.manager.sendSetScoreboardScore(other, title, name, health, BoardModifyType.UPDATE);
                }
            }
        });
    }

    // Do nothing. The only reason to call this is a courtesy to other players.
    public void removePlayer(final CDPlayer player) {}

    public void updatePlayer(final CDPlayer player) {
        final int health = player.getPlayer().getHealth();
        final String name = player.getOverheadName();
        CHTask.manager.runOrdered(new Runnable() {
            @Override
            public void run() {
                // Tell all players on the server the given player's name and health
                for(Player bukkitOther : Bukkit.getOnlinePlayers()) {
                    if(bukkitOther.getName().equals(player.getName())) {
                        continue;
                    }
                    CDPlayer other = CDPlayer.get(bukkitOther);
                    CHPacket.manager.sendSetScoreboardScore(other, title, name, health, BoardModifyType.UPDATE);
                }
            }
        });
    }
}
