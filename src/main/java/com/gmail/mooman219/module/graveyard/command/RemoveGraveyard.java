package com.gmail.mooman219.module.graveyard.command;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.graveyard.CCGraveyard;
import com.gmail.mooman219.module.graveyard.CMGraveyard;
import com.gmail.mooman219.module.graveyard.GraveyardManager;
import com.gmail.mooman219.module.graveyard.store.CSGraveyard;
import com.gmail.mooman219.module.service.DTPlayer;

public class RemoveGraveyard extends CCommand {
    public CCGraveyard module;

    public RemoveGraveyard(CCGraveyard module) {
        super("", Rank.GAMEMASTER, "/RemoveGraveyard");
        this.module = module;
    }

    @Override
    public void processPlayer(Player sender, DTPlayer playerData, String[] args) {
        CSGraveyard graveyardData = GraveyardManager.removeGraveyard(sender.getLocation());
        TextHelper.message(sender, CMGraveyard.F_REMOVE, graveyardData.getLocation().getBlockX(), graveyardData.getLocation().getBlockZ());
        WorldHelper.playEffect(graveyardData.getLocation(), Effect.MOBSPAWNER_FLAMES);
        WorldHelper.playSound(graveyardData.getLocation(), Sound.ENDERMAN_TELEPORT);
        module.storeGraveyard.save();
    }
}
