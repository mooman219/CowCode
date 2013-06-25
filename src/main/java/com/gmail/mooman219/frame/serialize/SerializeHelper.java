package com.gmail.mooman219.frame.serialize;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.gmail.mooman219.frame.ItemHelper;
import com.google.gson.Gson;

public class SerializeHelper {
    public static class BasicItemStack {
        public int id;         // ID
        public int am;         // Amount
        public short dm;       // Damage
        public byte da;        // Data
        public String na;      // Display Name
        public List<String> lo;// Lore

        public BasicItemStack(ItemStack itemstack) {
            id = itemstack.getTypeId();
            dm = itemstack.getDurability();
            am = itemstack.getAmount();
            da = itemstack.getData().getData();
            if(itemstack.getItemMeta() != null) {
                na = itemstack.getItemMeta().getDisplayName();
                lo = itemstack.getItemMeta().getLore();
            }
        }

        public ItemStack toItemStack() {
            ItemStack itemstack = new ItemStack(id, am, dm);
            itemstack.getData().setData(da);
            if(na != null) {
                ItemHelper.setName(itemstack, na);
            }
            if(lo != null) {
                ItemHelper.setLore(itemstack, lo);
            }
            return itemstack;
        }
    }

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
