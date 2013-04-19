package com.gmail.mooman219.module.region.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.CDPlayer;
import com.gmail.mooman219.module.region.CMRegion;
import com.gmail.mooman219.module.region.store.StoreRegionInformation;

public class NewInformation extends CCommand {
    public NewInformation() {
        super("string string", Rank.DEVELOPER, "/NewRegionInformation (id) (name)");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        if(StoreRegionInformation.getInformationByID(args[0]) != null) {
            TextHelper.message(sender, CMRegion.M_EXISTS);
        } else {
            StoreRegionInformation.addInformation(args[0], args[1]);
            TextHelper.message(sender, CMRegion.M_ADDED);
        }
    }
}
