package com.gmail.mooman219.frame.scoreboard;

import java.util.HashMap;

import org.bukkit.entity.Player;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.handler.packet.CHPacket;
import com.gmail.mooman219.module.CDPlayer;

public class Board {
    private final String title;
    private final Player player;
    private HashMap<String, BoardValue> rows;

    public Board(final Player player, final String title, final String displayTitle, final BoardDisplayType displayType) {
        this.player = player;
        this.rows = new HashMap<String, BoardValue>();
        this.title = title;

        CDPlayer.get(player).runTask(new Runnable() {
            @Override
            public void run() {
                CHPacket.manager.sendSetScoreboardObjective(player, title, displayTitle, BoardModifyType.UPDATE);
                CHPacket.manager.sendSetScoreboardDisplay(player, title, displayType);
            }
        });
    }

    public void modifyTitle(final String displayTitle) {
        CDPlayer.get(player).runTask(new Runnable() {
            @Override
            public void run() {
                CHPacket.manager.sendSetScoreboardObjective(player, title, displayTitle, BoardModifyType.TITLE);
            }
        });
    }

    public void addKey(final String key, final String name, final int value) {
        addKey(key, new BoardValue(name, value));
    }

    public void addKey(final String key, final BoardValue boardValue) {
        if(rows.containsKey(key)) {
            final BoardValue currentValue = rows.get(key);
            CDPlayer.get(player).runTask(new Runnable() {
                @Override
                public void run() {
                    CHPacket.manager.sendSetScoreboardScore(player, title, currentValue.getClientName(), currentValue.getValue(), BoardModifyType.REMOVE);
                    CHPacket.manager.sendSetScoreboardScore(player, title, boardValue.getName(), boardValue.getValue(), BoardModifyType.UPDATE);
                    currentValue.setName(boardValue.getName());
                    currentValue.setValue(boardValue.getValue());
                    currentValue.setClientName(currentValue.getName());
                }
            });
        } else {
            rows.put(key, boardValue);
            CDPlayer.get(player).runTask(new Runnable() {
                @Override
                public void run() {
                    CHPacket.manager.sendSetScoreboardScore(player, title, boardValue.getName(), boardValue.getValue(), BoardModifyType.UPDATE);
                }
            });
        }
    }

    public void removeKey(final String key) {
        if(rows.containsKey(key)) {
            final BoardValue boardValue = rows.get(key);
            rows.remove(key);
            CDPlayer.get(player).runTask(new Runnable() {
                @Override
                public void run() {
                    CHPacket.manager.sendSetScoreboardScore(player, title, boardValue.getClientName(), boardValue.getValue(), BoardModifyType.REMOVE);
                }
            });
        } else {
            Loader.warning("Scoreboard key doesn't exist '" + key + "'");
        }
    }

    public void modifyValue(final String key, final int value) {
        if(rows.containsKey(key)) {
            final BoardValue boardValue = rows.get(key);
            CDPlayer.get(player).runTask(new Runnable() {
                @Override
                public void run() {
                    boardValue.setValue(value);
                    CHPacket.manager.sendSetScoreboardScore(player, title, boardValue.getClientName(), boardValue.getValue(), BoardModifyType.UPDATE);
                }
            });
        } else {
            Loader.warning("Scoreboard key doesn't exist '" + key + "'");
        }
    }

    public void modifyName(final String key, final String name) {
        if(rows.containsKey(key)) {
            final BoardValue boardValue = rows.get(key);
            CDPlayer.get(player).runTask(new Runnable() {
                @Override
                public void run() {
                    boardValue.setName(name);
                    CHPacket.manager.sendSetScoreboardScore(player, title, boardValue.getClientName(), boardValue.getValue(), BoardModifyType.REMOVE);
                    CHPacket.manager.sendSetScoreboardScore(player, title, boardValue.getName(), boardValue.getValue(), BoardModifyType.UPDATE);
                    boardValue.setClientName(name);
                }
            });
        } else {
            Loader.warning("Scoreboard key doesn't exist '" + key + "'");
        }
    }
}
