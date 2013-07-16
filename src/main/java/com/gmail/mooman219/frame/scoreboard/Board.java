package com.gmail.mooman219.frame.scoreboard;

import java.util.HashMap;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.PacketHelper;

public class Board {
    private final String title;
    private final CDPlayer player;
    private final HashMap<String, BoardValue> rows;

    public Board(CDPlayer player, String title, String displayTitle, BoardDisplayType displayType) {
        this.player = player;
        this.rows = new HashMap<String, BoardValue>();
        this.title = title;

        player.sendPacket(PacketHelper.getSetScoreboardObjective(title, displayTitle, BoardModifyType.UPDATE));
        player.sendPacket(PacketHelper.getSetScoreboardDisplay(title, displayType));
    }

    public void modifyTitle(String displayTitle) {
        player.sendPacket(PacketHelper.getSetScoreboardObjective(title, displayTitle, BoardModifyType.TITLE));
    }

    public void addKey(String key, String name, int value) {
        addKey(key, new BoardValue(name, value));
    }

    public void addKey(String key, BoardValue boardValue) {
        BoardValue currentValue = rows.get(key);
        if(currentValue != null) {
            player.sendPacket(PacketHelper.getSetScoreboardScore(title, currentValue.getClientName(), currentValue.getValue(), BoardModifyType.REMOVE));
            player.sendPacket(PacketHelper.getSetScoreboardScore(title, boardValue.getName(), boardValue.getValue(), BoardModifyType.UPDATE));
            currentValue.setName(boardValue.getName());
            currentValue.setValue(boardValue.getValue());
            currentValue.setClientName(currentValue.getName());
        } else {
            rows.put(key, boardValue);
            player.sendPacket(PacketHelper.getSetScoreboardScore(title, boardValue.getName(), boardValue.getValue(), BoardModifyType.UPDATE));
        }
    }

    public void removeKey(String key) {
        BoardValue boardValue = rows.get(key);
        if(boardValue != null) {
            rows.remove(key);
            player.sendPacket(PacketHelper.getSetScoreboardScore(title, boardValue.getClientName(), boardValue.getValue(), BoardModifyType.REMOVE));
        } else {
            Loader.warning("Scoreboard key doesn't exist '" + key + "'");
        }
    }

    public void modifyValue(String key, int value) {
        BoardValue boardValue = rows.get(key);
        if(boardValue != null) {
            boardValue.setValue(value);
            player.sendPacket(PacketHelper.getSetScoreboardScore(title, boardValue.getClientName(), boardValue.getValue(), BoardModifyType.UPDATE));
        } else {
            Loader.warning("Scoreboard key doesn't exist '" + key + "'");
        }
    }

    public void modifyName(String key, String name) {
        BoardValue boardValue = rows.get(key);
        if(boardValue != null) {
            boardValue.setName(name);
            player.sendPacket(PacketHelper.getSetScoreboardScore(title, boardValue.getClientName(), boardValue.getValue(), BoardModifyType.REMOVE));
            player.sendPacket(PacketHelper.getSetScoreboardScore(title, boardValue.getName(), boardValue.getValue(), BoardModifyType.UPDATE));
            boardValue.setClientName(name);
        } else {
            Loader.warning("Scoreboard key doesn't exist '" + key + "'");
        }
    }
}
