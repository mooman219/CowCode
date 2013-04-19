package com.gmail.mooman219.module.region.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.CDPlayer;
import com.gmail.mooman219.module.region.CMRegion;
import com.gmail.mooman219.module.region.store.StoreRegionInformation;
import com.gmail.mooman219.module.region.store.CFRegion;

public class SetRegion extends CCommand {
    public SetRegion() {
        super("string", Rank.DEVELOPER, "/SetRegion (id)");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        if(StoreRegionInformation.getInformationByID(args[0]) != null) {
            CFRegion.getChunkRegion(sender).setCSRegionInformation(StoreRegionInformation.getInformationByID(args[0]));
            TextHelper.message(sender, CMRegion.M_MODIFIED);
        } else {
            TextHelper.message(sender, CMRegion.M_NONEXISTS);
        }
    }
}
