package com.gmail.mooman219.module.region.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.region.CMRegion;
import com.gmail.mooman219.module.region.store.CFInfo;
import com.gmail.mooman219.module.service.player.PlayerData;

public class NewInformation extends CCommand {
    public NewInformation() {
        super("string string", Rank.DEVELOPER, "/NewRegionInformation (id) (name)");
    }

    @Override
    public void processPlayer(Player sender, PlayerData playerData, String[] args) {
        if(CFInfo.getInformationByID(args[0]) != null) {
            TextHelper.message(sender, CMRegion.M_EXISTS);
        } else {
            CFInfo.addInformation(args[0], args[1]);
            TextHelper.message(sender, CMRegion.M_ADDED);
        }
    }
}
