package com.gmail.mooman219.module.mineral.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDChunk;
import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.mineral.CCMineral;

public class ClearMinerals extends CCommand {
    public CCMineral module;

    public ClearMinerals(CCMineral module) {
        super("clearminerals", Rank.GAMEMASTER, "/ClearMinerals");
        this.module = module;
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        CDChunk chunk = CDChunk.get(sender);
        CCMineral.FRM.CLEAR.send(sender, chunk.getMinerals().size());
        chunk.getMinerals().clear();
    }
}
