package com.gmail.mooman219.handler.database;

public enum UploadThread {
    ASYNC       (true,  false, true),
    ASYNC_REMOVE(true,  true,  true),
    SYNC        (false, false, false),
    SYNC_REMOVE (false, true,  false),
    SELF_REMOVE (false, true,  true);
    
    public final boolean async;
    public final boolean remove;
    public final boolean removeAsync;
    
    UploadThread(boolean async, boolean remove, boolean removeAsync) {
        this.async = async;
        this.remove = remove;
        this.removeAsync = removeAsync;
    }
}
