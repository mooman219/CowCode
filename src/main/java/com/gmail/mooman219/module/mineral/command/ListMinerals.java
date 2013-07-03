package com.gmail.mooman219.module.mineral.command;

import org.bukkit.Effect;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.serialize.json.BasicVectorInteger;
import com.gmail.mooman219.module.mineral.CCMineral;
import com.gmail.mooman219.module.mineral.store.BasicMineral;
import com.gmail.mooman219.module.mineral.store.StoreMineral;

public class ListMinerals extends CCommand {
    public ListMinerals() {
        super("listminerals", Rank.GAMEMASTER, "/ListMinerals");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        CCMineral.FRM.LIST_TITLE.send(sender, StoreMineral.getMinerals().size());
        int i = 0;
        for(BasicMineral mineral : StoreMineral.getMinerals().values()) {
            BasicVectorInteger bl = mineral.getBasicLocation().getVector();
            CCMineral.FRM.LIST.send(sender, i, bl.getX(), bl.getY(), bl.getZ(), mineral.getRespawnDelay() / 1000);
            WorldHelper.playEffect(mineral.getLocation(), Effect.MOBSPAWNER_FLAMES);
            i++;
        }
    }
}
