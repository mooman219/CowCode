package com.gmail.mooman219.module.graveyard.command;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.graveyard.CMGraveyard;
import com.gmail.mooman219.module.graveyard.GraveyardManager;
import com.gmail.mooman219.module.graveyard.store.CSGraveyard;
import com.gmail.mooman219.module.service.PlayerData;

public class TeleportGraveyard extends CCommand {
    public TeleportGraveyard() {
        super("number", Rank.GAMEMASTER, "/TeleportGraveyard (Grave ID)");
    }

    @Override
    public void processPlayer(Player sender, PlayerData playerData, String[] args) {
        CSGraveyard graveyardData = GraveyardManager.getGraveyard(Integer.parseInt(args[0]));
        if(graveyardData != null) {
            sender.teleport(graveyardData.getLocation());
            WorldHelper.playEffect(graveyardData.getLocation(), Effect.MOBSPAWNER_FLAMES);
            WorldHelper.playEffect(graveyardData.getLocation().clone().add(0, 1, 0), Effect.MOBSPAWNER_FLAMES);
            WorldHelper.playSound(graveyardData.getLocation(), Sound.ENDERMAN_TELEPORT);
            TextHelper.message(sender, CMGraveyard.F_TP, args[0]);
        } else {
            TextHelper.message(sender, CMGraveyard.F_TP_FAILED, args[0]);
        }
    }
}
