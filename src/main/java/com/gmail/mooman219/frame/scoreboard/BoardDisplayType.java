package com.gmail.mooman219.frame.scoreboard;

public enum BoardDisplayType {
    LIST(0),
    SIDEBAR(1),
    BELOWNAME(2);

    public final int id;

    BoardDisplayType(int id) {
        this.id = id;
    }

    public static BoardDisplayType getID(int id) {
        for(BoardDisplayType scoreboardDisplayType : BoardDisplayType.values()) {
            if(scoreboardDisplayType.id == id) {
                return scoreboardDisplayType;
            }
        }
        throw new IllegalArgumentException("Invalid ID of " + id);
    }
}
