package com.gmail.mooman219.module.graveyard.command;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.graveyard.CMGraveyard;
import com.gmail.mooman219.module.graveyard.store.StoreGraveyard;
import com.gmail.mooman219.module.service.player.PlayerData;

public class ListGraveyards extends CCommand {
    public ListGraveyards() {
        super("", Rank.GAMEMASTER, "/ListGraveyards");
    }

    @Override
    public void processPlayer(Player sender, PlayerData playerData, String[] args) {
        TextHelper.message(sender, CMGraveyard.F_LIST_TITLE , StoreGraveyard.graveyards.size());
        for(int i = 0; i < StoreGraveyard.graveyards.size(); i++) {
            Location location = StoreGraveyard.graveyards.get(i).getLocation().clone();
            TextHelper.message(sender, CMGraveyard.F_LIST , i, location.getBlockX(), location.getBlockY(), location.getBlockZ(), StoreGraveyard.graveyards.get(i).levelRequirement);
            WorldHelper.playEffect(location, Effect.MOBSPAWNER_FLAMES);
            WorldHelper.playEffect(location.add(0, 1, 0), Effect.MOBSPAWNER_FLAMES);
            WorldHelper.playSound(location, Sound.ENDERMAN_TELEPORT);
        }
    }

    @Override
    public void processConsole(CommandSender sender, String[] args) {
        TextHelper.message(sender, CMGraveyard.F_LIST_TITLE , StoreGraveyard.graveyards.size());
        for(int i = 0; i < StoreGraveyard.graveyards.size(); i++) {
            Location location = StoreGraveyard.graveyards.get(i).getLocation();
            TextHelper.message(sender, CMGraveyard.F_LIST , i, location.getBlockX(), location.getBlockY(), location.getBlockZ(), StoreGraveyard.graveyards.get(i).levelRequirement);
        }
    }
}
