package com.gmail.mooman219.module.service.command;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.NumberHelper;
import com.gmail.mooman219.frame.command.CCArg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.CDLiving;
import com.gmail.mooman219.module.CDPlayer;

public class EntityFace extends CCommand {
    public EntityFace() {
        super(Rank.REGULAR, "/EntityFace (Distance)", CCArg.INT);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        int distance = NumberHelper.toInt(args[0]);
        for(Entity entity : sender.getNearbyEntities(distance, distance, distance)) {
            if(entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                CDLiving.get(livingEntity).controller.face(sender);
            }
        }
    }
}
