package com.gmail.mooman219.module.region.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.region.CCRegion;
import com.gmail.mooman219.module.region.store.StoreRegionInfo;

public class NewRegion extends CCommand {
    public NewRegion() {
        super("newregion", Rank.DEVELOPER, "/NewRegion (id) (name)", Carg.STRING, Carg.MESSAGE);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        if(StoreRegionInfo.getInfoByID(args[0]) != null) {
            CCRegion.MSG.EXISTS.send(sender);
        } else {
            String name = TextHelper.merge(args, 1);
            StoreRegionInfo.addInfo(args[0], name);
            CCRegion.MSG.ADDED.send(sender);
        }
    }
}
