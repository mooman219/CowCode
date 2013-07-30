package com.gmail.mooman219.handler.database;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.CEventFactory;
import com.gmail.mooman219.handler.database.type.UploadReason;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class UploadRequest implements Runnable {
    private final CDPlayer player;
    private final UploadReason reason;
    private final boolean shouldRemove;
    private final boolean runAsync;

    public UploadRequest(CDPlayer player, UploadReason reason, boolean shouldRemove, boolean runAsync) {
        this.player = player;
        this.reason = reason;
        this.shouldRemove = shouldRemove;
        this.runAsync = runAsync;
    }

    @Override
    public void run() {
        try {
            if(shouldRemove) {
                CEventFactory.callDataRemovalEvent(runAsync, player);
            }
            DBObject playerObject = player.save(reason);
            WriteResult result = CHDatabase.getManager().getUsersCollection().update(new BasicDBObject("username", player.getUsername()), new BasicDBObject("$set", playerObject));
            if(result.getError() != null) {
                Loader.warning(CHDatabase.getCast() + "Mongo Error");
                Loader.warning(CHDatabase.getCast() + result.getError());
            }
            if(shouldRemove) {
                player.processRemoval();
            }
            Loader.info(CHDatabase.getCast() + "[UP] ["+reason.name()+"] : " + player.getUsername());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
