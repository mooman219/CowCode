package com.gmail.mooman219.module.region.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.math.NumberHelper;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.region.RegionManager;
import com.gmail.mooman219.module.region.CCRegion;
import com.gmail.mooman219.module.region.store.FastRegion;

public class ModifyLock extends CCommand {
    public ModifyLock() {
        super("modifylock", Rank.DEVELOPER, "/ModifyLock (id) (is locked)", Carg.STRING, Carg.BOOLEAN);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        FastRegion region = RegionManager.getRegion(args[0]);
        if(region != null) {
            region.setLocked(NumberHelper.toBoolean(args[1]));
            CCRegion.FRM.MODIFIED.send(sender, region.getID());
            Region.sendInfo(sender, region);
        } else {
            CCRegion.MSG.NONEXISTS.send(sender);
        }
    }
}
