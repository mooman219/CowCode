package com.gmail.mooman219.module.damage.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.vanilla.CCVanilla;

public class Heal extends CCommand {
    public Heal() {
        super("heal", Rank.DEVELOPER, "/Heal");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        sender.setSaturation(5f); // DON'T QUESTION MY REASONING FOR THIS
        playerData.heal(9001);
        CCVanilla.MSG.HEAL.send(sender);
    }
}
