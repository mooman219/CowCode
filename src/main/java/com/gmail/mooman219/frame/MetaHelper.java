package com.gmail.mooman219.frame;

import net.minecraft.server.NBTTagCompound;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class MetaHelper {    
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
    
    public static NBTTagCompound getItemstackTagCompound(ItemStack itemstack) {
        NBTTagCompound tag = new NBTTagCompound();
        CraftItemStack.asNMSCopy(itemstack).save(tag);
        return tag;
    }
}
