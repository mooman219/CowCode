package com.gmail.mooman219.module.graveyard.command;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.graveyard.CCGraveyard;
import com.gmail.mooman219.module.graveyard.store.DataGraveyard;

public class ListGraveyards extends CCommand {
    public ListGraveyards() {
        super("listgraveyards", Rank.GAMEMASTER, "/ListGraveyards");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        CCGraveyard.FRM.LIST_TITLE.send(sender, DataGraveyard.getGraveyards().size());
        for(int i = 0; i < DataGraveyard.getGraveyards().size(); i++) {
            Location location = DataGraveyard.getGraveyards().get(i).toLocation().clone();
            CCGraveyard.FRM.LIST.send(sender, i, location.getBlockX(), location.getBlockY(), location.getBlockZ(), DataGraveyard.getGraveyards().get(i).getLevel());
            WorldHelper.playEffect(location, Effect.MOBSPAWNER_FLAMES);
            WorldHelper.playEffect(location.add(0, 1, 0), Effect.MOBSPAWNER_FLAMES);
            WorldHelper.playSound(location, Sound.ENDERMAN_TELEPORT);
        }
    }

    @Override
    public void processConsole(CommandSender sender, String[] args) {
        CCGraveyard.FRM.LIST_TITLE.send(sender, DataGraveyard.getGraveyards().size());
        for(int i = 0; i < DataGraveyard.getGraveyards().size(); i++) {
            Location location = DataGraveyard.getGraveyards().get(i).toLocation();
            CCGraveyard.FRM.LIST.send(sender, i, location.getBlockX(), location.getBlockY(), location.getBlockZ(), DataGraveyard.getGraveyards().get(i).getLevel());
        }
    }
}
