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

public class TeleportClosestGraveyard extends CCommand {
    public TeleportClosestGraveyard() {
        super("teleportclosestgraveyard", Rank.GAMEMASTER, "/TeleportClosestGraveyard");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        FastGraveyard graveyardData = GraveyardManager.getClosestGraveyard(sender.getLocation());
        if(graveyardData != null) {
            sender.teleport(graveyardData.toLocation());
            WorldHelper.playEffect(graveyardData.toLocation(), Effect.MOBSPAWNER_FLAMES);
            WorldHelper.playEffect(graveyardData.toLocation().clone().add(0, 1, 0), Effect.MOBSPAWNER_FLAMES);
            WorldHelper.playSound(graveyardData.toLocation(), Sound.ENDERMAN_TELEPORT);
            CCGraveyard.MSG.TPCLOSE.send(sender);
        } else {
            CCGraveyard.MSG.TPCLOSE_FAILED.send(sender);
        }
    }
}
