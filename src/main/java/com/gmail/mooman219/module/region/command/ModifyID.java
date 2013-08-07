package com.gmail.mooman219.module.region.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.region.RegionManager;
import com.gmail.mooman219.module.region.CCRegion;
import com.gmail.mooman219.module.region.store.FastRegion;

public class ModifyID extends CCommand {
    public ModifyID() {
        super("modifyid", Rank.DEVELOPER, "/ModifyID (id) (new id)", Carg.STRING, Carg.STRING);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        FastRegion region = RegionManager.getRegion(args[0]);
        if(region != null) {
            region.setID(args[1]);
            CCRegion.FRM.MODIFIED.send(sender, region.getID());
            Region.sendInfo(sender, region);
        } else {
            CCRegion.MSG.NONEXISTS.send(sender);
        }
    }
}
