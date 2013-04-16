package com.gmail.mooman219.module.mineral.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.mineral.CMMineral;
import com.gmail.mooman219.module.mineral.store.StoreMineral;
import com.gmail.mooman219.module.service.DTPlayer;

public class TotalMinerals extends CCommand {
    public TotalMinerals() {
        super("", Rank.GAMEMASTER, "/TotalMinerals");
    }

    @Override
    public void processPlayer(Player sender, DTPlayer playerData, String[] args) {
        TextHelper.message(sender, CMMineral.F_TOTAL, StoreMineral.minerals.size());
    }

    @Override
    public void processConsole(CommandSender sender, String[] args) {
        TextHelper.message(sender, CMMineral.F_TOTAL, StoreMineral.minerals.size());
    }
}