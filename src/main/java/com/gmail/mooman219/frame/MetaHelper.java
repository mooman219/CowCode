package com.gmail.mooman219.frame;

import java.util.ArrayList;

import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.PendingConnection;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.CraftChunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import com.gmail.mooman219.craftbukkit.CSLiveStore;
import com.gmail.mooman219.craftbukkit.CSTagStore;
import com.gmail.mooman219.module.service.player.PlayerData;

public class MetaHelper {
    public static CSTagStore getTagStore(Entity entity) {
        return ((CraftEntity)entity).getHandle().csTagStore;
    }
    
    public static CSTagStore getTagStore(Chunk chunk) {
        return ((CraftChunk)chunk).getHandle().csTagStore;
    }
    
    public static CSLiveStore getStore(Entity entity) {
        return ((CraftEntity)entity).getHandle().csLiveStore;
    }
    
    public static CSLiveStore getStore(Chunk chunk) {
        return ((CraftChunk)chunk).getHandle().csLiveStore;
    }
    
    public static CSLiveStore getStore(AsyncPlayerPreLoginEvent event) {
        return ((PendingConnection) event.getPendingConnection()).ccLiveStore;
    }
    
    public static PlayerData getPlayerData(Player player) {
        return (PlayerData) getStore(player).get("playerdata", null);
    }

    public static ArrayList<PlayerData> getAllPlayerData() {
        ArrayList<PlayerData> data = new ArrayList<PlayerData>();
        for(Player player : Bukkit.getOnlinePlayers()) {
            data.add(getPlayerData(player));
        }
        return data;
    }
    
    public static NBTTagCompound getEntityTagCompound(Entity entity) {
        NBTTagCompound tag = new NBTTagCompound();
        ((CraftEntity)entity).getHandle().e(tag);
        return tag;
    }
    
    public static NBTTagCompound getLivingEntityTagCompound(LivingEntity entity) {
        NBTTagCompound tag = new NBTTagCompound();
        ((CraftLivingEntity)entity).getHandle().b(tag);
        return tag;
    }
}
