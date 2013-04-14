package com.gmail.mooman219.frame;

import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.PendingConnection;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import com.gmail.mooman219.bukkit.StoreLive;

public class MetaHelper {    
    public static StoreLive getStore(AsyncPlayerPreLoginEvent event) {
        return ((PendingConnection) event.getPendingConnection()).cStoreLive;
    }
    
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
    
    public static NBTTagCompound getWorldTagCompound(World world) {
        return ((CraftWorld)world).getHandle().getWorldData().a();
    }
    
    public static NBTTagCompound getChunkTagCompound(Chunk chunk) {
        NBTTagCompound tag = new NBTTagCompound();
        ((CraftLivingEntity)entity).getHandle().b(tag);
        return tag;
    }
}
