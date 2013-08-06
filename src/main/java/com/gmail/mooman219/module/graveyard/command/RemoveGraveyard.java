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
import com.gmail.mooman219.module.graveyard.store.FastGraveyard;

public class RemoveGraveyard extends CCommand {
    public RemoveGraveyard() {
        super("removegraveyard", Rank.GAMEMASTER, "/RemoveGraveyard");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        FastGraveyard graveyardData = GraveyardManager.removeGraveyard(sender.getLocation());
        CCGraveyard.FRM.REMOVE.send(sender, graveyardData.toLocation().getBlockX(), graveyardData.toLocation().getBlockZ());
        WorldHelper.playEffect(graveyardData.toLocation(), Effect.MOBSPAWNER_FLAMES);
        WorldHelper.playSound(graveyardData.toLocation(), Sound.ENDERMAN_TELEPORT);
    }
}
