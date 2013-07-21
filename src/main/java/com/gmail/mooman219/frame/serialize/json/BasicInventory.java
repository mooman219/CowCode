package com.gmail.mooman219.frame.serialize.json;

import java.util.Arrays;
import org.bukkit.inventory.ItemStack;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.layout.JsonData;
import com.google.gson.annotations.SerializedName;

public class BasicInventory implements JsonData {
    @SerializedName("Items") public BasicItemStack[] items;

    public BasicInventory(ItemStack... itemStacks) {
        items = new BasicItemStack[itemStacks.length];
        for(int i = 0; i < itemStacks.length ; i++) {
            if(itemStacks[i] != null) {
                items[i] = new BasicItemStack(itemStacks[i]);
            }
        }
    }

    public ItemStack[] toInventory() {
        ItemStack[] itemStacks = new ItemStack[items.length];
        for(int i = 0; i < items.length ; i++) {
            if(items[i] != null) {
                itemStacks[i] = items[i].toItemStack();
            }
        }
        return itemStacks;
    }

    /**
     * Json methods
     */

    @Override
    public String toString() {
        return JsonHelper.getGson().toJson(this);
    }

    public static BasicInventory fromString(String string) {
        return JsonHelper.getGson().fromJson(string, BasicInventory.class);
    }

    /**
     * Needed methods
     */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(items);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BasicInventory other = (BasicInventory) obj;
        if (!Arrays.equals(items, other.items)) {
            return false;
        }
        return true;
    }

}