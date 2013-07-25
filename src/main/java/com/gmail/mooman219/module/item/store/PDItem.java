package com.gmail.mooman219.module.item.store;

import org.bukkit.inventory.ItemStack;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.database.MongoHelper;
import com.gmail.mooman219.frame.serialize.json.BasicInventory;
import com.gmail.mooman219.handler.database.type.DownloadReason;
import com.gmail.mooman219.handler.database.type.UploadReason;
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

    public BasicInventory savedInventory = new BasicInventory();

    @Override
    public void sync(DownloadReason reason, DBObject chat) {
        switch(reason) {
        case LOGIN:
            savedInventory = BasicInventory.fromList(MongoHelper.getValue(chat, "inventory", new BasicInventory().toList()));
            // Save to the cache.
            cachedInventory = savedInventory.toInventory();
        case QUERY:
        default:
            break;

        }
    }

    @Override
    public DBObject getTemplate(UploadReason reason) {
        switch(reason) {
        case SAVE:
            // Pull from cache
            savedInventory = cachedInventory != null ? new BasicInventory(cachedInventory) : savedInventory;
            return new BasicDBObject()
            .append(getTag() + ".inventory", savedInventory.toList());
        case STATUS:
        default:
            return new BasicDBObject();
        }
    }

    /**
     * Live
     */

    /*
     * Cache is important. This means that we have 2 copies of the players inventory.
     * If the event happens where this inventory was never set, we can pull from the original one.
     */
    private ItemStack[] cachedInventory;

    public void setInventory(ItemStack[] inventory) {
        cachedInventory = inventory;
    }

    public ItemStack[] getInventory() {
        return cachedInventory;
    }

    @Override
    public void create() {}

    @Override
    public void destroy() {
        cachedInventory = null;
    }
}
