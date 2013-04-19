package com.gmail.mooman219.module.region.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.CDPlayer;
import com.gmail.mooman219.module.region.CMRegion;
import com.gmail.mooman219.module.region.store.StoreRegionInformation;

public class ModifyInformation extends CCommand {
    public ModifyInformation() {
        super("string message", Rank.DEVELOPER, "/ModifyInformation (id) (information)");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        if(StoreRegionInformation.getInformationByID(args[0]) != null) {
            StoreRegionInformation.getInformationByID(args[0]).description = TextHelper.merge(args, 1);
            TextHelper.message(sender, CMRegion.M_MODIFIED);
        } else {
            TextHelper.message(sender, CMRegion.M_NONEXISTS);
        }
    }
}
