package com.gmail.mooman219.module.mineral.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.CDPlayer;
import com.gmail.mooman219.module.mineral.CCMineral;
import com.gmail.mooman219.module.mineral.MineralManager;

public class RevertMinerals extends CCommand {
    public RevertMinerals() {
        super("revertminerals", Rank.GAMEMASTER, "/RevertMinerals");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        processConsole(sender, args);
    }

    @Override
    public void processConsole(CommandSender sender, String[] args) {
        MineralManager.revert();
        CCMineral.MSG.REVERT.send(sender);
    }
}
