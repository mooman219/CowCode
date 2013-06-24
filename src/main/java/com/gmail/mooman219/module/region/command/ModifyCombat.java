package com.gmail.mooman219.module.region.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.region.CCRegion;
import com.gmail.mooman219.module.region.store.CSRegionInfo;
import com.gmail.mooman219.module.region.store.StoreRegionInfo;
import com.gmail.mooman219.module.region.store.RegionCombatType;

public class ModifyCombat extends CCommand {
    public ModifyCombat() {
        super("modifycombat", Rank.DEVELOPER, "/ModifyCombat (id) (combat)", Carg.STRING, Carg.INT);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        CSRegionInfo region = StoreRegionInfo.getInfoByID(args[0]);
        if(region != null) {
            region.setCombatType(RegionCombatType.getID(Integer.parseInt(args[1])));
            CCRegion.MSG.MODIFIED.send(sender);
        } else {
            CCRegion.MSG.NONEXISTS.send(sender);
        }
    }
}
