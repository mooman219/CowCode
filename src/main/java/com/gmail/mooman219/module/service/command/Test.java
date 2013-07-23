package com.gmail.mooman219.module.service.command;

import org.bukkit.entity.Player;
import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.bull.PlayerAnimation;
import com.gmail.mooman219.frame.NumberHelper;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.rank.Rank;

public class Test extends CCommand {
    public Test() {
        super("test", Rank.REGULAR, "/Test (Flag - Create/Read)", Carg.BOOLEAN);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        boolean flag = NumberHelper.toBoolean(args[0]);
        if(flag) {
            playerData.playAnimation(PlayerAnimation.SIT);
        } else {
            playerData.playAnimation(PlayerAnimation.STOP_SITTING);
        }
    }
}
