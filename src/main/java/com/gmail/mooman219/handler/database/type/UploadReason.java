package com.gmail.mooman219.handler.database.type;

public enum UploadReason {
    STATUS(0),
    SAVE(1);

    private final int id;

    UploadReason(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }
}
