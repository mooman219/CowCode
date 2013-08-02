package com.gmail.mooman219.module.item.store;

import org.bukkit.inventory.ItemStack;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.database.MongoHelper;
import com.gmail.mooman219.frame.serialize.json.BasicInventory;
import com.gmail.mooman219.handler.database.type.DownloadReason;
import com.gmail.mooman219.handler.database.type.UploadReason;
import com.gmail.mooman219.layout.PlayerData;
import com.gmail.mooman219.module.item.stockpile.EquipmentStockpile;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class PDItem extends PlayerData {
    public PDItem(CDPlayer player) {
        super(player, "item");

        equipmentStockpile = new EquipmentStockpile();
    }

    /**
     * Offline
     */

    @Override
    public void load(DownloadReason reason, DBObject chat) {
        switch(reason) {
        case LOGIN:
            setEquipmentInventory(BasicInventory.fromList(MongoHelper.getValue(chat, "equipment", new BasicInventory().toList())).toInventory());
            setPlayerInventory(BasicInventory.fromList(MongoHelper.getValue(chat, "inventory", new BasicInventory().toList())).toInventory());
        case QUERY:
        default:
            break;

        }
    }

    @Override
    public DBObject save(UploadReason reason) {
        switch(reason) {
        case SAVE:
            return new BasicDBObject()
            .append(getTag() + ".equipment", BasicInventory.toList(getEquipmentInventory()))
            .append(getTag() + ".inventory", BasicInventory.toList(playerInventory));
        case STATUS:
        default:
            return new BasicDBObject();
        }
    }

    /**
     * Live
     */

    private EquipmentStockpile equipmentStockpile;
    private ItemStack[] playerInventory;

    public void setEquipmentInventory(ItemStack[] inventory) {
        equipmentStockpile.setSignificantItems(inventory);
    }

    public void setPlayerInventory(ItemStack[] inventory) {
        playerInventory = inventory;
    }

    public EquipmentStockpile getEquipment() {
        return equipmentStockpile;
    }

    public ItemStack[] getEquipmentInventory() {
        return equipmentStockpile.getSignificantItems();
    }

    public ItemStack[] getPlayerInventory() {
        return playerInventory;
    }

    @Override
    public void create() {}

    @Override
    public void destroy() {
        equipmentStockpile = null;
        playerInventory = null;
    }
}
