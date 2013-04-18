package com.gmail.mooman219.module.service.command;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.CDPlayer;

public class Test extends CCommand {
    public Test() {
        super("number number", Rank.REGULAR, "/Test");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        sender.removePotionEffect(PotionEffectType.JUMP);
        sender.addPotionEffect(PotionEffectType.JUMP.createEffect(Integer.parseInt(args[0]), Integer.parseInt(args[1])));
        //PacketHelper.test(sender, args[0], Integer.parseInt(args[1]));
    }
}
