package com.gmail.mooman219.module.item.store;

import org.bukkit.inventory.ItemStack;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.MongoHelper;
import com.gmail.mooman219.frame.serialize.json.BasicInventory;
import com.gmail.mooman219.handler.database.UploadReason;
import com.gmail.mooman219.layout.PlayerData;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class PDItem extends PlayerData {
    public PDItem(CDPlayer player) {
        super(player, "item");
    }

    /**
     * Offline
     */

    @Override
    public void sync(DBObject chat) {
        setInventory(BasicInventory.fromString(MongoHelper.getValue(chat, "inventory", new BasicInventory().toString())).toInventory());
    }

    @Override
    public DBObject getTemplate(UploadReason reason) {
        switch(reason) {
        case SAVE:
            return new BasicDBObject()
            .append(getTag() + ".inventory", new BasicInventory(playerInventory).toString());
        case STATUS:
        default:
            return new BasicDBObject();
        }
    }

    /**
     * Live
     */

    private ItemStack[] playerInventory;

    public void setInventory(ItemStack[] inventory) {
        playerInventory = inventory.clone();
    }

    public ItemStack[] getInventory() {
        return playerInventory.clone();
    }

    /**
     * No create this time around because of the special sync.
     * This in turn means we can process all the shit on a different thread.
     */
    @Override
    public void create() {}

    @Override
    public void destroy() {
        playerInventory = null;
    }
}
