package com.gmail.mooman219.frame.scoreboard;

import java.util.HashMap;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.handler.packet.CHPacket;

public class Board {
    private final String title;
    private final CDPlayer player;
    private final HashMap<String, BoardValue> rows;

    public Board(CDPlayer player, String title, String displayTitle, BoardDisplayType displayType) {
        this.player = player;
        this.rows = new HashMap<String, BoardValue>();
        this.title = title;

        CHPacket.manager.sendSetScoreboardObjective(player, title, displayTitle, BoardModifyType.UPDATE);
        CHPacket.manager.sendSetScoreboardDisplay(player, title, displayType);
    }

    public void modifyTitle(String displayTitle) {
        CHPacket.manager.sendSetScoreboardObjective(player, title, displayTitle, BoardModifyType.TITLE);
    }

    public void addKey(String key, String name, int value) {
        addKey(key, new BoardValue(name, value));
    }

    public void addKey(String key, BoardValue boardValue) {
        BoardValue currentValue = rows.get(key);
        if(currentValue != null) {
            CHPacket.manager.sendSetScoreboardScore(player, title, currentValue.getClientName(), currentValue.getValue(), BoardModifyType.REMOVE);
            CHPacket.manager.sendSetScoreboardScore(player, title, boardValue.getName(), boardValue.getValue(), BoardModifyType.UPDATE);
            currentValue.setName(boardValue.getName());
            currentValue.setValue(boardValue.getValue());
            currentValue.setClientName(currentValue.getName());
        } else {
            rows.put(key, boardValue);
            CHPacket.manager.sendSetScoreboardScore(player, title, boardValue.getName(), boardValue.getValue(), BoardModifyType.UPDATE);
        }
    }

    public void removeKey(String key) {
        BoardValue boardValue = rows.get(key);
        if(boardValue != null) {
            rows.remove(key);
            CHPacket.manager.sendSetScoreboardScore(player, title, boardValue.getClientName(), boardValue.getValue(), BoardModifyType.REMOVE);
        } else {
            Loader.warning("Scoreboard key doesn't exist '" + key + "'");
        }
    }

    public void modifyValue(String key, int value) {
        BoardValue boardValue = rows.get(key);
        if(boardValue != null) {
            boardValue.setValue(value);
            CHPacket.manager.sendSetScoreboardScore(player, title, boardValue.getClientName(), boardValue.getValue(), BoardModifyType.UPDATE);
        } else {
            Loader.warning("Scoreboard key doesn't exist '" + key + "'");
        }
    }

    public void modifyName(String key, String name) {
        BoardValue boardValue = rows.get(key);
        if(boardValue != null) {
            boardValue.setName(name);
            CHPacket.manager.sendSetScoreboardScore(player, title, boardValue.getClientName(), boardValue.getValue(), BoardModifyType.REMOVE);
            CHPacket.manager.sendSetScoreboardScore(player, title, boardValue.getName(), boardValue.getValue(), BoardModifyType.UPDATE);
            boardValue.setClientName(name);
        } else {
            Loader.warning("Scoreboard key doesn't exist '" + key + "'");
        }
    }
}
