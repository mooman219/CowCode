package com.gmail.mooman219.module.mineral.command;

import org.bukkit.Effect;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDChunk;
import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.mineral.CCMineral;
import com.gmail.mooman219.module.mineral.store.BasicMineral;

public class ListMinerals extends CCommand {
    public ListMinerals() {
        super("listminerals", Rank.GAMEMASTER, "/ListMinerals");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        CDChunk chunk = CDChunk.get(sender);
        CCMineral.FRM.LIST_TITLE.send(sender, chunk.getMinerals().size());
        int i = 0;
        for(BasicMineral mineral : chunk.getMinerals()) {
            CCMineral.FRM.LIST.send(sender, i, mineral.x, mineral.y, mineral.z, mineral.respawnDelay);
            WorldHelper.playEffect(mineral.getLocation(chunk), Effect.MOBSPAWNER_FLAMES);
            i++;
        }
    }
}
