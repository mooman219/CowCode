package com.gmail.mooman219.old.frame;

import java.util.HashMap;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.scoreboard.BoardDisplayType;
import com.gmail.mooman219.frame.scoreboard.BoardModifyType;
import com.gmail.mooman219.frame.scoreboard.BoardValue;
import com.gmail.mooman219.handler.packet.CHPacket;
import com.gmail.mooman219.handler.task.CHTask;

public class HealthBoard {
    private final String title;
    private final String displayTitle;
    private final HashMap<CDPlayer, BoardValue> rows;

    public HealthBoard(final String title, final String displayTitle) {
        this.title = title;
        this.displayTitle = displayTitle;
        this.rows = new HashMap<CDPlayer, BoardValue>();
    }

    public void addPlayer(final CDPlayer player) {
        if(rows.containsKey(player)) {
            updatePlayer(player);
            Loader.warning("Scoreboard key already exists '" + player.getName() + "'");
        } else {
            final int health = player.getPlayer().getHealth();
            CHTask.manager.runOrdered(new Runnable() {
                @Override
                public void run() {
                    // Create the board on the client for the new player
                    CHPacket.manager.sendSetScoreboardObjective(player, title, displayTitle, BoardModifyType.UPDATE);
                    CHPacket.manager.sendSetScoreboardDisplay(player, title, BoardDisplayType.BELOWNAME);
                    // Give the new player all current entries in the board
                    for(BoardValue other : rows.values()) {
                        CHPacket.manager.sendSetScoreboardScore(player, title, other.getClientName(), other.getValue(), BoardModifyType.UPDATE);
                    }
                    // Add player to the board
                    final BoardValue target = rows.put(player, new BoardValue(player.getOverheadName(), health));
                    // Tell all players on the board that the new player has joined
                    for(CDPlayer other : rows.keySet()) {
                        CHPacket.manager.sendSetScoreboardScore(other, title, target.getClientName(), target.getValue(), BoardModifyType.UPDATE);
                    }
                }
            });
        }
    }

    public void removePlayer(final CDPlayer player) {
        if(!rows.containsKey(player)) {
            Loader.warning("Scoreboard key doesn't exist '" + player.getName() + "'");
        } else {
            CHTask.manager.runOrdered(new Runnable() {
                @Override
                public void run() {
                    // Get the player's BoardValue from the board
                    final BoardValue target =  rows.get(player);
                    // Tell all players on the board that a player has left
                    for(CDPlayer other : rows.keySet()) {
                        CHPacket.manager.sendSetScoreboardScore(other, title, target.getClientName(), target.getValue(), BoardModifyType.REMOVE);
                    }
                    // Remove the player from the board
                    rows.remove(player);
                }
            });
        }
    }

    public void updatePlayer(final CDPlayer player) {
        if(!rows.containsKey(player)) {
            Loader.warning("Scoreboard key doesn't exist '" + player.getName() + "'");
        } else {
            final int health = player.getPlayer().getHealth();
            CHTask.manager.runOrdered(new Runnable() {
                @Override
                public void run() {
                    // Get the player's BoardValue from the board
                    final BoardValue target = rows.get(player);
                    // Update the value to the new one
                    target.setValue(health);
                    // Tell all players on the board that a value has updated
                    for(CDPlayer other : rows.keySet()) {
                        CHPacket.manager.sendSetScoreboardScore(other, title, target.getClientName(), target.getValue(), BoardModifyType.UPDATE);
                    }
                }
            });
        }
    }
}
