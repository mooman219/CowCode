package com.gmail.mooman219.module.graveyard.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.CDPlayer;
import com.gmail.mooman219.module.graveyard.CMGraveyard;
import com.gmail.mooman219.module.graveyard.store.StoreGraveyard;

public class TotalGraveyards extends CCommand {
    public TotalGraveyards() {
        super("totalgraveyards", Rank.GAMEMASTER, "/GetTotalGraveyards");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        TextHelper.message(sender, CMGraveyard.F_TOTAL, StoreGraveyard.graveyards.size());
    }

    @Override
    public void processConsole(CommandSender sender, String[] args) {
        TextHelper.message(sender, CMGraveyard.F_TOTAL, StoreGraveyard.graveyards.size());
    }
}
