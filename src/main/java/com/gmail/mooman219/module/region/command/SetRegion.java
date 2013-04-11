package com.gmail.mooman219.module.region.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.region.CMRegion;
import com.gmail.mooman219.module.region.store.CFInfo;
import com.gmail.mooman219.module.region.store.CFRegion;
import com.gmail.mooman219.module.service.player.PlayerData;

public class SetRegion extends CCommand {
    public SetRegion() {
        super("string", Rank.DEVELOPER, "/SetRegion (id)");
    }

    @Override
    public void processPlayer(Player sender, PlayerData playerData, String[] args) {
        if(CFInfo.getInformationByID(args[0]) != null) {
            CFRegion.getRegion(sender).setCSRegionInformation(CFInfo.getInformationByID(args[0]));
            TextHelper.message(sender, CMRegion.M_MODIFIED);
        } else {
            TextHelper.message(sender, CMRegion.M_NONEXISTS);
        }
    }
}
