package com.gmail.mooman219.frame.serialize;

import org.bukkit.inventory.ItemStack;

import com.gmail.mooman219.frame.serialize.json.BasicItemStack;
import com.google.gson.Gson;

public class SerializeHelper {
    public static String toStringFromItemStack(ItemStack itemstack) {
        Gson gson = new Gson();
        String json = gson.toJson(new BasicItemStack(itemstack));
        return json;
    }

    public static ItemStack toItemStackFromString(String string) {
        Gson gson = new Gson();
        BasicItemStack basicItemStack = gson.fromJson(string, BasicItemStack.class);
        return basicItemStack.toItemStack();
    }
}
