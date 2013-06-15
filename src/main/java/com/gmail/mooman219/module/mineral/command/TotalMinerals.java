package com.gmail.mooman219.module.mineral.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bullbukkit.CDChunk;
import com.gmail.mooman219.bullbukkit.CDPlayer;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.mineral.CCMineral;

public class TotalMinerals extends CCommand {
    public TotalMinerals() {
        super("totalminerals", Rank.MODERATOR, "/TotalMinerals");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        CCMineral.FRM.TOTAL.send(sender, CDChunk.get(sender).minerals.size());
    }
}