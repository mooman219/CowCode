package com.gmail.mooman219.frame.scoreboard;

public enum ScoreboardModifyType {
    UPDATE(0),
    REMOVE(1),
    TITLE(2);

    public final int id;

    ScoreboardModifyType(int id) {
        this.id = id;
    }

    public static ScoreboardModifyType getID(int id) {
        for(ScoreboardModifyType scoreboardModifyType : ScoreboardModifyType.values()) {
            if(scoreboardModifyType.id == id) {
                return scoreboardModifyType;
            }
        }
        throw new IllegalArgumentException("Invalid ID of " + id);
    }
}
