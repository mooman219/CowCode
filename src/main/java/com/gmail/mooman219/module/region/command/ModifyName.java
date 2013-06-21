package com.gmail.mooman219.module.region.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.region.CCRegion;
import com.gmail.mooman219.module.region.store.CSRegionInformation;
import com.gmail.mooman219.module.region.store.StoreRegionInformation;

public class ModifyName extends CCommand {
    public ModifyName() {
        super("modifyname", Rank.DEVELOPER, "/ModifyName (id) (new name)", Carg.STRING, Carg.STRING);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        CSRegionInformation region = StoreRegionInformation.getInformationByID(args[0]);
        if(region != null) {
            region.name = args[1];
            CCRegion.MSG.MODIFIED.send(sender);
        } else {
            CCRegion.MSG.NONEXISTS.send(sender);
        }
    }
}
