package com.gmail.mooman219.module.vanilla.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.math.NumberHelper;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.vanilla.CCVanilla;

public class Suicide extends CCommand {
    public Suicide() {
        super("suicide", Rank.REGULAR, "/Suicide");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        playerData.damage(9001);
        if(NumberHelper.random() < 0.001) { // 0.1%
            CCVanilla.MSG.SUICIDE_LOL.send(sender);
        } else {            
            CCVanilla.MSG.SUICIDE.send(sender);
        }
    }
}
