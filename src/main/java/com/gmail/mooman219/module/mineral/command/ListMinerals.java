package com.gmail.mooman219.module.mineral.command;

import org.bukkit.Effect;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.serialize.jack.FastLocation;
import com.gmail.mooman219.module.mineral.CCMineral;
import com.gmail.mooman219.module.mineral.store.FastMineral;
import com.gmail.mooman219.module.mineral.store.StoreMineral;

public class ListMinerals extends CCommand {
    public ListMinerals() {
        super("listminerals", Rank.GAMEMASTER, "/ListMinerals");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        CCMineral.FRM.LIST_TITLE.send(sender, StoreMineral.getMinerals().size());
        int i = 0;
        for(FastMineral mineral : StoreMineral.getMinerals().values()) {
            FastLocation loc = mineral.getLocation();
            CCMineral.FRM.LIST.send(sender, i, loc.getX(), loc.getY(), loc.getZ(), mineral.getRespawnDelay() / 1000);
            WorldHelper.playEffect(mineral.toLocation(), Effect.MOBSPAWNER_FLAMES);
            i++;
        }
    }
}
