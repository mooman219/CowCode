package com.gmail.mooman219.bull;

import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.PacketHelper;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet17EntityLocationAction;
import net.minecraft.server.Packet40EntityMetadata;

public enum PlayerAnimation {
    ARM_SWING {
        @Override
        protected void playAnimation(EntityPlayer player, int radius) {
            PacketHelper.sendNearby(player.getBukkitEntity().getLocation(), radius, PacketHelper.getArmAnimation(player, 1));
        }
    },
    CRIT {
        @Override
        protected void playAnimation(EntityPlayer player, int radius) {
            PacketHelper.sendNearby(player.getBukkitEntity().getLocation(), radius, PacketHelper.getArmAnimation(player, 6));
        }
    },
    HURT {
        @Override
        protected void playAnimation(EntityPlayer player, int radius) {
            PacketHelper.sendNearby(player.getBukkitEntity().getLocation(), radius, PacketHelper.getArmAnimation(player, 2));
        }
    },
    MAGIC_CRIT {
        @Override
        protected void playAnimation(EntityPlayer player, int radius) {
            PacketHelper.sendNearby(player.getBukkitEntity().getLocation(), radius, PacketHelper.getArmAnimation(player, 7));
        }
    },
    SIT {
        @Override
        protected void playAnimation(EntityPlayer player, int radius) {
            player.mount(player);
        }
    },
    SLEEP {
        @Override
        protected void playAnimation(EntityPlayer player, int radius) {
            Packet17EntityLocationAction packet = new Packet17EntityLocationAction(player, 0, (int) player.locX, (int) player.locY, (int) player.locZ);
            PacketHelper.sendNearby(player.getBukkitEntity().getLocation(), radius, packet);
        }
    },
    SNEAK {
        @Override
        protected void playAnimation(EntityPlayer player, int radius) {
            player.getBukkitEntity().setSneaking(true);
            PacketHelper.sendNearby(player.getBukkitEntity().getLocation(), radius, new Packet40EntityMetadata(player.id, player.getDataWatcher(), true));
        }
    },
    STOP_SITTING {
        @Override
        protected void playAnimation(EntityPlayer player, int radius) {
            player.mount(null);
        }
    },
    STOP_SLEEPING {
        @Override
        protected void playAnimation(EntityPlayer player, int radius) {
            PacketHelper.sendNearby(player.getBukkitEntity().getLocation(), radius, PacketHelper.getArmAnimation(player, 3));
        }
    },
    STOP_SNEAKING {
        @Override
        protected void playAnimation(EntityPlayer player, int radius) {
            player.getBukkitEntity().setSneaking(false);
            PacketHelper.sendNearby(player.getBukkitEntity().getLocation(), radius, new Packet40EntityMetadata(player.id, player.getDataWatcher(), true));
        }
    };

    public void play(Player player) {
        play(player, 64);
    }

    public void play(Player player, int radius) {
        playAnimation(((CraftPlayer) player).getHandle(), radius);
    }

    protected void playAnimation(EntityPlayer player, int radius) {
        throw new UnsupportedOperationException("unimplemented animation");
    }
}