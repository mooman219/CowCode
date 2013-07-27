package com.gmail.mooman219.frame.team;

public enum TeamModifyType {
    CREATED(0),
    REMOVED(1),
    UPDATED(2),
    PLAYER_ADD(3),
    PLAYER_REMOVE(4);

    private final int id;

    TeamModifyType(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public static TeamModifyType getID(int id) {
        for(TeamModifyType scoreboardModifyType : TeamModifyType.values()) {
            if(scoreboardModifyType.id == id) {
                return scoreboardModifyType;
            }
        }
        throw new IllegalArgumentException("Invalid ID of " + id);
    }
}
