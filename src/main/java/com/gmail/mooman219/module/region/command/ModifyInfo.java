package com.gmail.mooman219.module.region.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.region.RegionManager;
import com.gmail.mooman219.module.region.CCRegion;
import com.gmail.mooman219.module.region.store.BasicRegion;

public class ModifyInfo extends CCommand {
    public ModifyInfo() {
        super("modifyinfo", Rank.DEVELOPER, "/ModifyInfo (id) (info)", Carg.STRING, Carg.MESSAGE);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        BasicRegion region = RegionManager.getInfoByID(args[0]);
        if(region != null) {
            region.setDescription(TextHelper.merge(args, 1));
            CCRegion.MSG.MODIFIED.send(sender);
        } else {
            CCRegion.MSG.NONEXISTS.send(sender);
        }
    }
}
