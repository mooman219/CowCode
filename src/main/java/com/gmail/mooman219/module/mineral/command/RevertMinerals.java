package com.gmail.mooman219.module.mineral.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDChunk;
import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.mineral.CCMineral;
import com.gmail.mooman219.module.mineral.store.Mineral;

public class RevertMinerals extends CCommand {
    public RevertMinerals() {
        super("revertminerals", Rank.GAMEMASTER, "/RevertMinerals");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        CCMineral.FRM.REVERT.send(sender, CDChunk.get(sender).getMinerals().size());
        for(Mineral mineral : CDChunk.get(sender).getMinerals()) {
            mineral.revert(sender.getLocation().getChunk());
        }
    }
}
