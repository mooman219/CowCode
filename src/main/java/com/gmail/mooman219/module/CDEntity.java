package com.gmail.mooman219.module;

import net.minecraft.server.NBTTagCompound;

import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.TagHelper;

public class CDEntity {
    public final Entity entity;

    private CDEntity(Entity entity) {
        this.entity = entity;
        loadTag();
    }

    /*
     * Live
     */

    // None

    /*
     * Tag
     */

    private int test = 0;

    public void setTest(int test) {
        this.test = test;
        getHandle().dataTag.setInt("test", test);
    }

    public int getTest() {
        return test;
    }

    public void loadTag() {
        NBTTagCompound tag = getHandle().dataTag;
        test = TagHelper.getInt(tag, "test", test);
    }

    /*
     * Default
     */

    public net.minecraft.server.Entity getHandle() {
        return ((CraftEntity)entity).getHandle();
    }

    public static CDEntity get(Entity entity) {
        net.minecraft.server.Entity handle = ((CraftEntity)entity).getHandle();
        if(entity instanceof Player) {
            throw new IllegalArgumentException("Players are not considered entities.");
        }
        if(handle.dataLive == null) {
            handle.dataLive = new CDEntity(entity);
        } else if(!(handle.dataLive instanceof CDEntity)) {
            throw new IllegalArgumentException("Invalid data on entity.");
        }
        return (CDEntity) handle.dataLive;
    }
}
