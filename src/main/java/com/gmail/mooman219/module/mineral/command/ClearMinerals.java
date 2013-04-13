package com.gmail.mooman219.module.mineral.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.mineral.CCMineral;
import com.gmail.mooman219.module.mineral.CMMineral;
import com.gmail.mooman219.module.mineral.store.StoreMineral;
import com.gmail.mooman219.module.service.PlayerData;

public class ClearMinerals extends CCommand {
    public CCMineral module;

    public ClearMinerals(CCMineral module) {
        super("", Rank.GAMEMASTER, "/ClearMinerals");
        this.module = module;
    }

    @Override
    public void processPlayer(Player sender, PlayerData playerData, String[] args) {
        TextHelper.message(sender, CMMineral.F_CLEAR, StoreMineral.minerals.size());
        StoreMineral.minerals.clear();
        module.storeMineral.save();
    }

    @Override
    public void processConsole(CommandSender sender, String[] args) {
        TextHelper.message(sender, CMMineral.F_CLEAR, StoreMineral.minerals.size());
        StoreMineral.minerals.clear();
        module.storeMineral.save();
    }
}
