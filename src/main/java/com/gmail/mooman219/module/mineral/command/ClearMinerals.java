package com.gmail.mooman219.module.mineral.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.mineral.CCMineral;
import com.gmail.mooman219.module.mineral.MineralManager;

public class ClearMinerals extends CCommand {
    public CCMineral module;

    public ClearMinerals(CCMineral module) {
        super("clearminerals", Rank.GAMEMASTER, "/ClearMinerals");
        this.module = module;
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        CCMineral.FRM.CLEAR.send(sender, MineralManager.clear());
    }
}
