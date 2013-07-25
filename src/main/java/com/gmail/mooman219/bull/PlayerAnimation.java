package com.gmail.mooman219.bull;

import org.bukkit.craftbukkit.v1_6_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.PacketHelper;

import net.minecraft.server.v1_6_R2.EntityPlayer;

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
            PacketHelper.sendNearby(player.getBukkitEntity().getLocation(), radius, PacketHelper.getEntityLocationAction(player.getBukkitEntity(), 0));
        }
    },
    SNEAK {
        @Override
        protected void playAnimation(EntityPlayer player, int radius) {
            player.getBukkitEntity().setSneaking(true);
            PacketHelper.sendNearby(player.getBukkitEntity().getLocation(), radius, PacketHelper.getEntityMetadata(player.getBukkitEntity()));
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
            PacketHelper.sendNearby(player.getBukkitEntity().getLocation(), radius, PacketHelper.getEntityMetadata(player.getBukkitEntity()));
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