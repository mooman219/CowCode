package com.gmail.mooman219.module.chat.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCArg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.CDPlayer;
import com.gmail.mooman219.module.chat.CMChat;

public class SetOverheadName extends CCommand {
    public SetOverheadName() {
        super(Rank.DEVELOPER, "/SetOverheadName (Desired Name)", CCArg.STRING);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        sender.setOverHeadName(args[0]);
        TextHelper.message(sender, CMChat.F_SETOVERHEAD, sender.getOverHeadName());
    }
}
