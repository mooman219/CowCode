package com.gmail.mooman219.module.region.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.CDChunk;
import com.gmail.mooman219.module.CDPlayer;
import com.gmail.mooman219.module.region.CCRegion;
import com.gmail.mooman219.module.region.store.CSRegionInformation;
import com.gmail.mooman219.module.region.store.StoreRegionInformation;

public class SetRegion extends CCommand {
    public SetRegion() {
        super("setregion", Rank.DEVELOPER, "/SetRegion (id)", Carg.STRING);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        CSRegionInformation region = StoreRegionInformation.getInformationByID(args[0]);
        if(region != null) {
            CDChunk.get(sender).setParentInformation(region);
            CCRegion.MSG.MODIFIED.send(sender);
        } else {
            CCRegion.MSG.NONEXISTS.send(sender);
        }
    }
}
