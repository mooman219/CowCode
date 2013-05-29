package com.gmail.mooman219.module.mineral.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bullbukkit.CDPlayer;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.mineral.CCMineral;
import com.gmail.mooman219.module.mineral.store.StoreMineral;

public class ClearMinerals extends CCommand {
    public CCMineral module;

    public ClearMinerals(CCMineral module) {
        super("clearminerals", Rank.GAMEMASTER, "/ClearMinerals");
        this.module = module;
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        processConsole(sender, args);
    }

    @Override
    public void processConsole(CommandSender sender, String[] args) {
        CCMineral.FRM.CLEAR.send(sender, StoreMineral.minerals.size());
        StoreMineral.minerals.clear();
        module.storeMineral.save();
    }
}
