package com.gmail.mooman219.frame.scoreboard;

import java.util.HashMap;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.handler.packet.CHPacket;

public class Board {
    private final String title;
    private final CDPlayer player;
    private HashMap<String, BoardValue> rows;

    public Board(final CDPlayer player, final String title, final String displayTitle, final BoardDisplayType displayType) {
        this.player = player;
        this.rows = new HashMap<String, BoardValue>();
        this.title = title;

        CHPacket.manager.sendSetScoreboardObjective(player, title, displayTitle, BoardModifyType.UPDATE);
        CHPacket.manager.sendSetScoreboardDisplay(player, title, displayType);
    }

    public void modifyTitle(final String displayTitle) {
        CHPacket.manager.sendSetScoreboardObjective(player, title, displayTitle, BoardModifyType.TITLE);
    }

    public void addKey(final String key, final String name, final int value) {
        addKey(key, new BoardValue(name, value));
    }

    public void addKey(final String key, final BoardValue boardValue) {
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

    public void removeKey(final String key) {
        BoardValue boardValue = rows.get(key);
        if(boardValue != null) {
            rows.remove(key);
            CHPacket.manager.sendSetScoreboardScore(player, title, boardValue.getClientName(), boardValue.getValue(), BoardModifyType.REMOVE);
        } else {
            Loader.warning("Scoreboard key doesn't exist '" + key + "'");
        }
    }

    public void modifyValue(final String key, final int value) {
        BoardValue boardValue = rows.get(key);
        if(boardValue != null) {
            boardValue.setValue(value);
            CHPacket.manager.sendSetScoreboardScore(player, title, boardValue.getClientName(), boardValue.getValue(), BoardModifyType.UPDATE);
        } else {
            Loader.warning("Scoreboard key doesn't exist '" + key + "'");
        }
    }

    public void modifyName(final String key, final String name) {
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
