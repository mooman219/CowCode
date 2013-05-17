package com.gmail.mooman219.module.region.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.CDPlayer;
import com.gmail.mooman219.module.region.CCRegion;
import com.gmail.mooman219.module.region.store.StoreRegionInformation;

public class NewInformation extends CCommand {
    public NewInformation() {
        super("newinformation", Rank.DEVELOPER, "/NewRegionInformation (id) (name)", Carg.STRING, Carg.STRING);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        if(StoreRegionInformation.getInformationByID(args[0]) != null) {
            CCRegion.MSG.EXISTS.send(sender);
        } else {
            StoreRegionInformation.addInformation(args[0], args[1]);
            CCRegion.MSG.ADDED.send(sender);
        }
    }
}
