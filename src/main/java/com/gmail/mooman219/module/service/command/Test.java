package com.gmail.mooman219.module.service.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;

public class Test extends CCommand {
    public Test() {
        super("test", Rank.REGULAR, "/Test");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        sender.openInventory(Bukkit.createInventory(sender, 9, "TEST - 0"));
    }
}
