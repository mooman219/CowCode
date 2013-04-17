package com.gmail.mooman219.module.graveyard.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.DLPlayer;
import com.gmail.mooman219.module.graveyard.CCGraveyard;
import com.gmail.mooman219.module.graveyard.CMGraveyard;
import com.gmail.mooman219.module.graveyard.store.StoreGraveyard;

public class ClearGraveyards extends CCommand {
    public CCGraveyard module;

    public ClearGraveyards(CCGraveyard module) {
        super("", Rank.GAMEMASTER, "/ClearGraveyards");
        this.module = module;
    }

    @Override
    public void processPlayer(Player sender, DLPlayer playerData, String[] args) {
        TextHelper.message(sender, CMGraveyard.F_CLEAR, StoreGraveyard.graveyards.size());
        StoreGraveyard.graveyards.clear();
        module.storeGraveyard.save();
    }

    @Override
    public void processConsole(CommandSender sender, String[] args) {
        TextHelper.message(sender, CMGraveyard.F_CLEAR, StoreGraveyard.graveyards.size());
        StoreGraveyard.graveyards.clear();
        module.storeGraveyard.save();
    }
}
