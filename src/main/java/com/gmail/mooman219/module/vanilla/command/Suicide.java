package com.gmail.mooman219.module.vanilla.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.vanilla.CCVanilla;

public class Suicide extends CCommand {
    public Suicide() {
        super("suicide", Rank.REGULAR, "/Suicide");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        playerData.damage(9001);
        CCVanilla.MSG.SUICIDE.send(sender);
    }
}
