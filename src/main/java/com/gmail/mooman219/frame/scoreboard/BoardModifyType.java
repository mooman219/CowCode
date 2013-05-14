package com.gmail.mooman219.frame.scoreboard;

public enum BoardModifyType {
    UPDATE(0),
    REMOVE(1),
    TITLE(2);

    public final int id;

    BoardModifyType(int id) {
        this.id = id;
    }

    public static BoardModifyType getID(int id) {
        for(BoardModifyType scoreboardModifyType : BoardModifyType.values()) {
            if(scoreboardModifyType.id == id) {
                return scoreboardModifyType;
            }
        }
        throw new IllegalArgumentException("Invalid ID of " + id);
    }
}
