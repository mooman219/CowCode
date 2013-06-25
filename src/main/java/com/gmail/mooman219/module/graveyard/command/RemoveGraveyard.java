package com.gmail.mooman219.module.graveyard.command;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.graveyard.CCGraveyard;
import com.gmail.mooman219.module.graveyard.GraveyardManager;
import com.gmail.mooman219.module.graveyard.store.BasicGraveyard;

public class RemoveGraveyard extends CCommand {
    public CCGraveyard module;

    public RemoveGraveyard(CCGraveyard module) {
        super("removegraveyard", Rank.GAMEMASTER, "/RemoveGraveyard");
        this.module = module;
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        BasicGraveyard graveyardData = GraveyardManager.removeGraveyard(sender.getLocation());
        CCGraveyard.FRM.REMOVE.send(sender, graveyardData.getLocation().getBlockX(), graveyardData.getLocation().getBlockZ());
        WorldHelper.playEffect(graveyardData.getLocation(), Effect.MOBSPAWNER_FLAMES);
        WorldHelper.playSound(graveyardData.getLocation(), Sound.ENDERMAN_TELEPORT);
        module.storeGraveyard.save();
    }
}
