package com.gmail.mooman219.module.region.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.CDPlayer;
import com.gmail.mooman219.module.region.CMRegion;
import com.gmail.mooman219.module.region.store.CSRegionInformation;
import com.gmail.mooman219.module.region.store.StoreRegionInformation;
import com.gmail.mooman219.module.region.store.RegionCombatType;

public class ModifyCombat extends CCommand {
    public ModifyCombat() {
        super("string number", Rank.DEVELOPER, "/ModifyCombat (id) (combat)");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        CSRegionInformation region = StoreRegionInformation.getInformationByID(args[0]);
        if(region != null) {
            region.combatType = RegionCombatType.getID(Integer.parseInt(args[1]));
            TextHelper.message(sender, CMRegion.M_MODIFIED);
        } else {
            TextHelper.message(sender, CMRegion.M_NONEXISTS);
        }
    }
}
