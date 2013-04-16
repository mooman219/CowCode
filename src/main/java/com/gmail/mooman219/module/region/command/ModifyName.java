package com.gmail.mooman219.module.region.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.region.CMRegion;
import com.gmail.mooman219.module.region.store.CFInfo;
import com.gmail.mooman219.module.service.DTPlayer;

public class ModifyName extends CCommand {
    public ModifyName() {
        super("string string", Rank.DEVELOPER, "/ModifyName (id) (new name)");
    }

    @Override
    public void processPlayer(Player sender, DTPlayer playerData, String[] args) {
        if(CFInfo.getInformationByID(args[0]) != null) {
            CFInfo.getInformationByID(args[0]).name = args[1];
            TextHelper.message(sender, CMRegion.M_MODIFIED);
        } else {
            TextHelper.message(sender, CMRegion.M_NONEXISTS);
        }
    }
}
