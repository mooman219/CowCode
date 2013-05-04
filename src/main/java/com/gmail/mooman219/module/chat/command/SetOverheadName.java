package com.gmail.mooman219.module.chat.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.CDPlayer;

public class SetOverheadName extends CCommand {
    public SetOverheadName() {
        super("string", Rank.DEVELOPER, "/Name (Desired Name)");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        sender.setOverHeadName(args[0]);
    }
}
