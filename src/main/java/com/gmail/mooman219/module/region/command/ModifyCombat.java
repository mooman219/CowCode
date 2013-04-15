package com.gmail.mooman219.module.region.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.region.CMRegion;
import com.gmail.mooman219.module.region.store.RegionCombatType;
import com.gmail.mooman219.module.service.PlayerData;

public class ModifyCombat extends CCommand {
    public ModifyCombat() {
        super("string number", Rank.DEVELOPER, "/ModifyCombat (id) (combat)");
    }

    @Override
    public void processPlayer(Player sender, PlayerData playerData, String[] args) {
        if(CFInfo.getInformationByID(args[0]) != null) {
            CFInfo.getInformationByID(args[0]).combatType = RegionCombatType.getID(Integer.parseInt(args[1]));
            TextHelper.message(sender, CMRegion.M_MODIFIED);
        } else {
            TextHelper.message(sender, CMRegion.M_NONEXISTS);
        }
    }
}
