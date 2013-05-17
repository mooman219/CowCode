package com.gmail.mooman219.module.chat.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.CDPlayer;
import com.gmail.mooman219.module.chat.CCChat;

public class SetOverheadName extends CCommand {
    public SetOverheadName() {
        super("setoverheadname", Rank.DEVELOPER, "/SetOverheadName (Desired Name)", Carg.STRING);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        sender.setOverHeadName(args[0]);
        CCChat.FRM.SETOVERHEAD.send(sender, sender.getOverHeadName());
    }
}
