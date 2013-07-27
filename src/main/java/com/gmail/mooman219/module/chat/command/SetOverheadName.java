package com.gmail.mooman219.module.chat.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;

public class SetOverheadName extends CCommand {
    public SetOverheadName() {
        super("setoverheadname", Rank.GAMEMASTER, "/SetOverheadName (Desired Name)", Carg.STRING);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        //playerData.setOverheadName(args[0]);
        //CCChat.FRM.SETOVERHEAD.send(sender, playerData.getOverheadName());
        sender.sendMessage("Shit tyrone, FEATURES REMOVED");
    }
}
