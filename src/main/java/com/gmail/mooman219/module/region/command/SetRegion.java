package com.gmail.mooman219.module.region.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.region.CCRegion;
import com.gmail.mooman219.module.region.RegionManager;
import com.gmail.mooman219.module.region.store.BasicRegion;

public class SetRegion extends CCommand {
    public SetRegion() {
        super("setregion", Rank.DEVELOPER, "/SetRegion (id)", Carg.STRING);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        BasicRegion region = RegionManager.gerRegion(args[0]);
        if(region != null) {
            RegionManager.setRegion(sender.getLocation(), region);
            CCRegion.FRM.MODIFIED.send(sender, region.getID());
        } else {
            CCRegion.MSG.NONEXISTS.send(sender);
        }
    }
}
