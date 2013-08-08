package com.gmail.mooman219.module.graveyard.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.graveyard.CCGraveyard;
import com.gmail.mooman219.module.graveyard.store.DataGraveyard;

public class TotalGraveyards extends CCommand {
    public TotalGraveyards() {
        super("totalgraveyards", Rank.GAMEMASTER, "/GetTotalGraveyards");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        processConsole(sender, args);
    }

    @Override
    public void processConsole(CommandSender sender, String[] args) {
        CCGraveyard.FRM.TOTAL.send(sender, DataGraveyard.getGraveyards().size());
    }
}
