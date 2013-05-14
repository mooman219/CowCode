package com.gmail.mooman219.handler.database;

public enum UploadThread {
    ASYNC       (true,  false, true),  //                      Upload (async)
    ASYNC_REMOVE(true,  true,  true),  // Call removal (async) Upload (async)
    SYNC        (false, false, false), //                      Upload (sync)
    SYNC_REMOVE (false, true,  false), // Call removal (sync)  Upload (async)
    SELF_REMOVE (false, true,  true);  // Call removal (async) Upload (async)
    
    public final boolean async;
    public final boolean remove;
    public final boolean removeAsync;
    
    UploadThread(boolean async, boolean remove, boolean removeAsync) {
        this.async = async;
        this.remove = remove;
        this.removeAsync = removeAsync;
    }
}
