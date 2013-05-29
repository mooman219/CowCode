package com.gmail.mooman219.module.mineral.command;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bullbukkit.CDPlayer;
import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.mineral.CCMineral;
import com.gmail.mooman219.module.mineral.store.CSMineral;
import com.gmail.mooman219.module.mineral.store.StoreMineral;

public class ListMinerals extends CCommand {
    public ListMinerals() {
        super("listminerals", Rank.GAMEMASTER, "/ListMinerals");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        CCMineral.FRM.LIST_TITLE.send(sender, StoreMineral.minerals.size());
        int i = 0;
        for(CSMineral mineralData : StoreMineral.minerals.values()) {
            Location location = mineralData.getLocation();
            CCMineral.FRM.LIST.send(sender, i, StoreMineral.minerals.size(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), mineralData.respawnDelay);
            WorldHelper.playEffect(location, Effect.MOBSPAWNER_FLAMES);
            WorldHelper.playEffect(location, Effect.MOBSPAWNER_FLAMES);
            i++;
        }
    }

    @Override
    public void processConsole(CommandSender sender, String[] args) {
        CCMineral.FRM.LIST_TITLE.send(sender, StoreMineral.minerals.size());
        int i = 0;
        for(CSMineral mineralData : StoreMineral.minerals.values()) {
            Location location = mineralData.getLocation();
            CCMineral.FRM.LIST.send(sender, i, StoreMineral.minerals.size(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), mineralData.respawnDelay);
            i++;
        }
    }
}
