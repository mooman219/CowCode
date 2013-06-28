package com.gmail.mooman219.module.region.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.region.CCRegion;
import com.gmail.mooman219.module.region.store.BasicRegionInfo;
import com.gmail.mooman219.module.region.store.StoreRegionInfo;

public class ModifyID extends CCommand {
    public ModifyID() {
        super("modifyid", Rank.DEVELOPER, "/modifyid (id) (new id)", Carg.STRING, Carg.STRING);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        BasicRegionInfo region = StoreRegionInfo.getInfoByID(args[0]);
        if(region != null) {
            region.setID(args[1]);
            CCRegion.MSG.MODIFIED.send(sender);
        } else {
            CCRegion.MSG.NONEXISTS.send(sender);
        }
    }
}
