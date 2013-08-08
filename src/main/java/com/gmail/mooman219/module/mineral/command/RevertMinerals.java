package com.gmail.mooman219.module.mineral.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.mineral.CCMineral;
import com.gmail.mooman219.module.mineral.MineralManager;
import com.gmail.mooman219.module.mineral.store.DataMineral;

public class RevertMinerals extends CCommand {
    public RevertMinerals() {
        super("revertminerals", Rank.GAMEMASTER, "/RevertMinerals");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        CCMineral.FRM.REVERT.send(sender, DataMineral.getMinerals().size());
        MineralManager.revert();
    }
}
