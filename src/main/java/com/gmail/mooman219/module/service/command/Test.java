package com.gmail.mooman219.module.service.command;

import net.minecraft.server.EntityCreature;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.PathfinderGoalMeleeAttack;
import net.minecraft.server.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.PathfinderGoalTarget;

import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.NumberHelper;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.CDEntity;
import com.gmail.mooman219.module.CDLiving;
import com.gmail.mooman219.module.CDPlayer;

public class Test extends CCommand {
    public Test() {
        super(Rank.REGULAR, "/Test");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        for(Entity entity : sender.getNearbyEntities(5, 5, 5)) {
            //net.minecraft.server.Entity handle = ((CraftEntity)entity).getHandle();
            if(entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                CDLiving.get(livingEntity).controller.face(sender);
            }
        }
        //sender.sendMessage("test: " + playerData.test++);
        /**
        float x = NumberHelper.toFloat(args[0]) / 100f;
        float y = NumberHelper.toFloat(args[1]) / 100f;
        float z = NumberHelper.toFloat(args[2]) / 100f;
        for(Entity entity : sender.getNearbyEntities(5, 5, 5)) {
            if(entity instanceof CraftCreature) {
                EntityCreature handle = ((CraftCreature) entity).getHandle();
                handle.motX = x;
                handle.motY = y;
                handle.motZ = z;
                //handle.bI = 0.3f;
                //handle.goalSelector.a(0, new PathfinderGoalNearestAttackableTarget(handle, EntityHuman.class, 16.0F, 0, true));
                //handle.targetSelector.a(0, new PathfinderGoalNearestAttackableTarget(handle, EntityHuman.class, 16.0F, 0, true));
            }
        }
        //sender.setOverHeadName(args[0]);
        //sender.removePotionEffect(PotionEffectType.JUMP);
        //sender.addPotionEffect(PotionEffectType.JUMP.createEffect(Integer.parseInt(args[0]), Integer.parseInt(args[1])));
        //PacketHelper.test(sender, args[0], Integer.parseInt(args[1]));
        /**/
    }
}
