package com.gmail.mooman219.module.region.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.region.RegionManager;
import com.gmail.mooman219.module.region.CCRegion;
import com.gmail.mooman219.module.region.api.RegionCombatType;
import com.gmail.mooman219.module.region.store.FastRegion;

public class ModifyCombat extends CCommand {
    public ModifyCombat() {
        super("modifycombat", Rank.DEVELOPER, "/ModifyCombat (id) (combat)", Carg.STRING, Carg.INT);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        FastRegion region = RegionManager.getRegion(args[0]);
        if(region != null) {
            region.setCombatType(RegionCombatType.fromID(Integer.parseInt(args[1])));
            CCRegion.FRM.MODIFIED.send(sender, region.getID());
            Region.sendInfo(sender, region);
        } else {
            CCRegion.MSG.NONEXISTS.send(sender);
        }
    }
}
