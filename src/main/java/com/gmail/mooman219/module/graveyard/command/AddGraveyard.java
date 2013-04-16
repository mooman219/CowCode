package com.gmail.mooman219.module.graveyard.command;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.graveyard.CCGraveyard;
import com.gmail.mooman219.module.graveyard.CMGraveyard;
import com.gmail.mooman219.module.graveyard.GraveyardManager;
import com.gmail.mooman219.module.graveyard.store.StoreGraveyard;
import com.gmail.mooman219.module.service.DTPlayer;

public class AddGraveyard extends CCommand {
    public CCGraveyard module;

    public AddGraveyard(CCGraveyard module) {
        super("number", Rank.GAMEMASTER, "/AddGraveyard (Level)");
        this.module = module;
    }

    @Override
    public void processPlayer(Player sender, DTPlayer playerData, String[] args) {
        Location location = sender.getLocation().clone().add(0, 0.1, 0);
        GraveyardManager.addGraveyard(location, Integer.parseInt(args[0]));
        WorldHelper.playEffect(location, Effect.MOBSPAWNER_FLAMES);
        WorldHelper.playEffect(location.add(0, 1, 0), Effect.MOBSPAWNER_FLAMES);
        WorldHelper.playSound(sender.getLocation(), Sound.ENDERMAN_TELEPORT);
        TextHelper.message(sender, CMGraveyard.F_ADD, StoreGraveyard.graveyards.size());
        module.storeGraveyard.save();
    }
}
