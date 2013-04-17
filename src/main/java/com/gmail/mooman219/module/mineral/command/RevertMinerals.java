package com.gmail.mooman219.module.mineral.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.DLPlayer;
import com.gmail.mooman219.module.mineral.CMMineral;
import com.gmail.mooman219.module.mineral.MineralManager;

public class RevertMinerals extends CCommand {
    public RevertMinerals() {
        super("", Rank.GAMEMASTER, "/RevertMinerals");
    }

    @Override
    public void processPlayer(Player sender, DLPlayer playerData, String[] args) {
        MineralManager.revert();
        TextHelper.message(sender, CMMineral.M_REVERT);
    }

    @Override
    public void processConsole(CommandSender sender, String[] args) {
        MineralManager.revert();
        TextHelper.message(sender, CMMineral.M_REVERT);
    }
}
