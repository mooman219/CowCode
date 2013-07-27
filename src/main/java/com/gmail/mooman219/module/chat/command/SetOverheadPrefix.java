package com.gmail.mooman219.module.chat.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.chat.CCChat;

public class SetOverheadPrefix extends CCommand {
    public SetOverheadPrefix() {
        super("setoverheadprefix", Rank.GAMEMASTER, "/SetOverheadPrefix (prefix)", Carg.STRING);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        playerData.setOverheadPrefix(args[0]);
        CCChat.FRM.SETOVERHEAD.send(sender, playerData.getOverheadPrefix() + playerData.getUsername() + playerData.getOverheadSuffix());
    }
}
