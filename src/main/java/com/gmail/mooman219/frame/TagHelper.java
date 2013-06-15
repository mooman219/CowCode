package com.gmail.mooman219.frame;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import net.minecraft.server.NBTTagCompound;

public class TagHelper {
    public static NBTTagCompound getEntityTagCompound(Entity entity) {
        NBTTagCompound tag = new NBTTagCompound();
        ((CraftEntity)entity).getHandle().e(tag);
        return tag;
    }

    public static NBTTagCompound getLivingEntityTagCompound(LivingEntity livingEntity) {
        NBTTagCompound tag = new NBTTagCompound();
        ((CraftLivingEntity)livingEntity).getHandle().b(tag);
        return tag;
    }

    public static byte getByte(NBTTagCompound tag, String key, byte fallback) {
        return tag.hasKey(key) ? tag.getByte(key) : fallback;
    }

    public static short getShort(NBTTagCompound tag, String key, short fallback) {
        return tag.hasKey(key) ? tag.getShort(key) : fallback;
    }

    public static int getInt(NBTTagCompound tag, String key, int fallback) {
        return tag.hasKey(key) ? tag.getInt(key) : fallback;
    }
    
    public static int[] getIntArray(NBTTagCompound tag, String key, int[] fallback) {
        return tag.hasKey(key) ? tag.getIntArray(key) : fallback;
    }

    public static long getLong(NBTTagCompound tag, String key, long fallback) {
        return tag.hasKey(key) ? tag.getLong(key) : fallback;
    }

    public static float getFloat(NBTTagCompound tag, String key, float fallback) {
        return tag.hasKey(key) ? tag.getFloat(key) : fallback;
    }

    public static double getDouble(NBTTagCompound tag, String key, double fallback) {
        return tag.hasKey(key) ? tag.getDouble(key) : fallback;
    }

    public static String getString(NBTTagCompound tag, String key, String fallback) {
        return tag.hasKey(key) ? tag.getString(key) : fallback;
    }
    
    public static Material getMaterial(NBTTagCompound tag, String key, Material fallback) {
        if(tag.hasKey(key)) {
            Material ret = Material.getMaterial(tag.getString(key));
            if(ret != null) {
                return ret;
            }
        }
        return fallback;
    }

    public static NBTTagCompound getCompound(NBTTagCompound tag, String key, NBTTagCompound fallback) {
        return tag.hasKey(key) ? tag.getCompound(key) : fallback;
    }

    public static <T> T getValue(Map<String, Object> map, String key, T fallback) {
        if(map.containsKey(key)) {
            try {
                return (T) map.get(key);
            } catch(ClassCastException e) {
                e.printStackTrace();
            }
        }
        return fallback;
    }
    
    public static void setMaterial(NBTTagCompound tag, String key, Material material) {
        tag.setString(key, material.name());
    }
}
