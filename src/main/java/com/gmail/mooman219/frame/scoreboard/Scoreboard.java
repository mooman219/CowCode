package com.gmail.mooman219.frame.scoreboard;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.handler.packet.CHPacket;

public class Scoreboard {
    public final String title;
    public final ScoreboardDisplayType scoreboardDisplayType;
    private String displayTitle;
    private ArrayList<Player> watchers;
    private HashMap<String, ScoreboardValue> rows;

    public Scoreboard(String title, ScoreboardDisplayType scoreboardDisplayType, String displayTitle) {
        this.watchers = new ArrayList<Player>();
        this.scoreboardDisplayType = scoreboardDisplayType;
        this.rows = new HashMap<String, ScoreboardValue>();
        this.displayTitle = displayTitle;
        this.title = title;
    }

    public void addWatcher(Player player) {
        if(watchers.contains(player)) {
            CHPacket.manager.sendSetScoreboardObjective(player, title, displayTitle, ScoreboardModifyType.REMOVE);
        } else {
            watchers.add(player);
        }
        CHPacket.manager.sendSetScoreboardObjective(player, title, displayTitle, ScoreboardModifyType.UPDATE);
        for(ScoreboardValue scoreboardValue : rows.values()) {
            CHPacket.manager.sendSetScoreboardScore(player, title, scoreboardValue.getClientName(), scoreboardValue.getValue(), ScoreboardModifyType.UPDATE);
        }
        CHPacket.manager.sendSetScoreboardDisplay(player, title, scoreboardDisplayType);
    }

    public void removeWatcher(Player player) {
        if(watchers.contains(player)) {
            CHPacket.manager.sendSetScoreboardObjective(player, title, displayTitle, ScoreboardModifyType.REMOVE);
            watchers.remove(player);
        }
    }

    public void modifyTitle(String displayTitle) {
        this.displayTitle = displayTitle;
        for(Player player : watchers) {
            CHPacket.manager.sendSetScoreboardObjective(player, title, this.displayTitle, ScoreboardModifyType.TITLE);
        }
    }

    public void addKey(String key, String name, int value) {
        addKey(key, new ScoreboardValue(name, value));
    }

    public void addKey(String key, ScoreboardValue scoreboardValue) {
        if(rows.containsKey(key)) {
            ScoreboardValue currentValue = rows.get(key);
            for(Player player : watchers) {
                CHPacket.manager.sendSetScoreboardScore(player, title, currentValue.getClientName(), currentValue.getValue(), ScoreboardModifyType.REMOVE);
                CHPacket.manager.sendSetScoreboardScore(player, title, scoreboardValue.getName(), scoreboardValue.getValue(), ScoreboardModifyType.UPDATE);
            }
            currentValue.setName(scoreboardValue.getName());
            currentValue.setValue(scoreboardValue.getValue());
            currentValue.setClientName(currentValue.getName());
        } else {
            rows.put(key, scoreboardValue);
            for(Player player : watchers) {
                CHPacket.manager.sendSetScoreboardScore(player, title, scoreboardValue.getName(), scoreboardValue.getValue(), ScoreboardModifyType.UPDATE);
            }
        }
    }

    public void removeKey(String key) {
        if(rows.containsKey(key)) {
            ScoreboardValue scoreboardValue = rows.get(key);
            rows.remove(key);
            for(Player player : watchers) {
                CHPacket.manager.sendSetScoreboardScore(player, title, scoreboardValue.getClientName(), scoreboardValue.getValue(), ScoreboardModifyType.REMOVE);
            }
        } else {
            Loader.warning("Scoreboard key doesn't exist '" + key + "'");
        }
    }

    public void modifyValue(String key, int value) {
        if(rows.containsKey(key)) {
            ScoreboardValue scoreboardValue = rows.get(key);
            scoreboardValue.setValue(value);
            for(Player player : watchers) {
                CHPacket.manager.sendSetScoreboardScore(player, title, scoreboardValue.getClientName(), scoreboardValue.getValue(), ScoreboardModifyType.UPDATE);
            }
        } else {
            Loader.warning("Scoreboard key doesn't exist '" + key + "'");
        }
    }

    public void modifyName(String key, String name) {
        if(rows.containsKey(key)) {
            ScoreboardValue scoreboardValue = rows.get(key);
            scoreboardValue.setName(name);
            for(Player player : watchers) {
                CHPacket.manager.sendSetScoreboardScore(player, title, scoreboardValue.getClientName(), scoreboardValue.getValue(), ScoreboardModifyType.REMOVE);
                CHPacket.manager.sendSetScoreboardScore(player, title, scoreboardValue.getName(), scoreboardValue.getValue(), ScoreboardModifyType.UPDATE);
            }
            scoreboardValue.setClientName(name);
        } else {
            Loader.warning("Scoreboard key doesn't exist '" + key + "'");
        }
    }
}
