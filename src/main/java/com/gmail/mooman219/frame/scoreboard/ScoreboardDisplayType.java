package com.gmail.mooman219.frame.scoreboard;

public enum ScoreboardDisplayType {
    LIST(0),
    SIDEBAR(1),
    BELOWNAME(2);

    public final int id;

    ScoreboardDisplayType(int id) {
        this.id = id;
    }

    public static ScoreboardDisplayType getID(int id) {
        for(ScoreboardDisplayType scoreboardDisplayType : ScoreboardDisplayType.values()) {
            if(scoreboardDisplayType.id == id) {
                return scoreboardDisplayType;
            }
        }
        throw new IllegalArgumentException("Invalid ID of " + id);
    }
}
