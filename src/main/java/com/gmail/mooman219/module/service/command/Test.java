package com.gmail.mooman219.module.service.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.CDPlayer;

public class Test extends CCommand {
    public Test() {
        super("test", Rank.REGULAR, "/Test");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {

    }
}
