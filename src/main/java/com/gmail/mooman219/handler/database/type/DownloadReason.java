package com.gmail.mooman219.handler.database.type;

public enum DownloadReason {
    QUERY(0),
    LOGIN(1);

    private final int id;

    DownloadReason(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }
}
