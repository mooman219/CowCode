package com.gmail.mooman219.frame;

import net.minecraft.server.NBTTagCompound;

public class TagHelper {
    public static byte getByte(NBTTagCompound tag, String key, byte fallback) {
        return tag.hasKey(key) ? tag.getByte(key) : fallback;
    }
    
    public static short getShort(NBTTagCompound tag, String key, short fallback) {
        return tag.hasKey(key) ? tag.getShort(key) : fallback;
    }
    
    public static int getInt(NBTTagCompound tag, String key, int fallback) {
        return tag.hasKey(key) ? tag.getInt(key) : fallback;
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
    
    public static NBTTagCompound getCompound(NBTTagCompound tag, String key, NBTTagCompound fallback) {
        return tag.hasKey(key) ? tag.getCompound(key) : fallback;
    }
}
