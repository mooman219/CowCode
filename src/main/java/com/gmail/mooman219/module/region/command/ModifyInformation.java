package com.gmail.mooman219.module.region.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.CDPlayer;
import com.gmail.mooman219.module.region.CMRegion;
import com.gmail.mooman219.module.region.store.CSRegionInformation;
import com.gmail.mooman219.module.region.store.StoreRegionInformation;

public class ModifyInformation extends CCommand {
    public ModifyInformation() {
        super("modifyinformation", Rank.DEVELOPER, "/ModifyInformation (id) (information)", Carg.STRING, Carg.MESSAGE);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        CSRegionInformation region = StoreRegionInformation.getInformationByID(args[0]);
        if(region != null) {
            region.description = TextHelper.merge(args, 1);
            TextHelper.message(sender, CMRegion.M_MODIFIED);
        } else {
            TextHelper.message(sender, CMRegion.M_NONEXISTS);
        }
    }
}
