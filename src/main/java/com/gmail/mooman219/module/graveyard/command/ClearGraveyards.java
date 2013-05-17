package com.gmail.mooman219.module.graveyard.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.CDPlayer;
import com.gmail.mooman219.module.graveyard.CCGraveyard;
import com.gmail.mooman219.module.graveyard.store.StoreGraveyard;

public class ClearGraveyards extends CCommand {
    public CCGraveyard module;

    public ClearGraveyards(CCGraveyard module) {
        super("cleargraveyards", Rank.GAMEMASTER, "/ClearGraveyards");
        this.module = module;
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        processConsole(sender, args);
    }

    @Override
    public void processConsole(CommandSender sender, String[] args) {
        CCGraveyard.FRM.CLEAR.send(sender, StoreGraveyard.graveyards.size());
        StoreGraveyard.graveyards.clear();
        module.storeGraveyard.save();
    }
}
