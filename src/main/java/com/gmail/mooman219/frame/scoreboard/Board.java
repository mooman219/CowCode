package com.gmail.mooman219.frame.scoreboard;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.handler.packet.CHPacket;

public class Board {
    public final String title;
    public final BoardDisplayType scoreboardDisplayType;
    private String displayTitle;
    private ArrayList<Player> watchers;
    private HashMap<String, BoardValue> rows;

    public Board(String title, BoardDisplayType scoreboardDisplayType, String displayTitle) {
        this.watchers = new ArrayList<Player>();
        this.scoreboardDisplayType = scoreboardDisplayType;
        this.rows = new HashMap<String, BoardValue>();
        this.displayTitle = displayTitle;
        this.title = title;
    }

    public void addWatcher(Player player) {
        if(watchers.contains(player)) {
            CHPacket.manager.sendSetScoreboardObjective(player, title, displayTitle, BoardModifyType.REMOVE);
        } else {
            watchers.add(player);
        }
        CHPacket.manager.sendSetScoreboardObjective(player, title, displayTitle, BoardModifyType.UPDATE);
        for(BoardValue scoreboardValue : rows.values()) {
            CHPacket.manager.sendSetScoreboardScore(player, title, scoreboardValue.getClientName(), scoreboardValue.getValue(), BoardModifyType.UPDATE);
        }
        CHPacket.manager.sendSetScoreboardDisplay(player, title, scoreboardDisplayType);
    }

    public void removeWatcher(Player player) {
        if(watchers.contains(player)) {
            CHPacket.manager.sendSetScoreboardObjective(player, title, displayTitle, BoardModifyType.REMOVE);
            watchers.remove(player);
        }
    }

    public void modifyTitle(String displayTitle) {
        this.displayTitle = displayTitle;
        for(Player player : watchers) {
            CHPacket.manager.sendSetScoreboardObjective(player, title, this.displayTitle, BoardModifyType.TITLE);
        }
    }

    public void addKey(String key, String name, int value) {
        addKey(key, new BoardValue(name, value));
    }

    public void addKey(String key, BoardValue scoreboardValue) {
        if(rows.containsKey(key)) {
            BoardValue currentValue = rows.get(key);
            for(Player player : watchers) {
                CHPacket.manager.sendSetScoreboardScore(player, title, currentValue.getClientName(), currentValue.getValue(), BoardModifyType.REMOVE);
                CHPacket.manager.sendSetScoreboardScore(player, title, scoreboardValue.getName(), scoreboardValue.getValue(), BoardModifyType.UPDATE);
            }
            currentValue.setName(scoreboardValue.getName());
            currentValue.setValue(scoreboardValue.getValue());
            currentValue.setClientName(currentValue.getName());
        } else {
            rows.put(key, scoreboardValue);
            for(Player player : watchers) {
                CHPacket.manager.sendSetScoreboardScore(player, title, scoreboardValue.getName(), scoreboardValue.getValue(), BoardModifyType.UPDATE);
            }
        }
    }

    public void removeKey(String key) {
        if(rows.containsKey(key)) {
            BoardValue scoreboardValue = rows.get(key);
            rows.remove(key);
            for(Player player : watchers) {
                CHPacket.manager.sendSetScoreboardScore(player, title, scoreboardValue.getClientName(), scoreboardValue.getValue(), BoardModifyType.REMOVE);
            }
        } else {
            Loader.warning("Scoreboard key doesn't exist '" + key + "'");
        }
    }

    public void modifyValue(String key, int value) {
        if(rows.containsKey(key)) {
            BoardValue scoreboardValue = rows.get(key);
            scoreboardValue.setValue(value);
            for(Player player : watchers) {
                CHPacket.manager.sendSetScoreboardScore(player, title, scoreboardValue.getClientName(), scoreboardValue.getValue(), BoardModifyType.UPDATE);
            }
        } else {
            Loader.warning("Scoreboard key doesn't exist '" + key + "'");
        }
    }

    public void modifyName(String key, String name) {
        if(rows.containsKey(key)) {
            BoardValue scoreboardValue = rows.get(key);
            scoreboardValue.setName(name);
            for(Player player : watchers) {
                CHPacket.manager.sendSetScoreboardScore(player, title, scoreboardValue.getClientName(), scoreboardValue.getValue(), BoardModifyType.REMOVE);
                CHPacket.manager.sendSetScoreboardScore(player, title, scoreboardValue.getName(), scoreboardValue.getValue(), BoardModifyType.UPDATE);
            }
            scoreboardValue.setClientName(name);
        } else {
            Loader.warning("Scoreboard key doesn't exist '" + key + "'");
        }
    }
}
