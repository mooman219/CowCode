package com.gmail.mooman219.module.graveyard.command;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.graveyard.CCGraveyard;
import com.gmail.mooman219.module.graveyard.GraveyardManager;
import com.gmail.mooman219.module.graveyard.store.FastGraveyard;

public class TeleportGraveyard extends CCommand {
    public TeleportGraveyard() {
        super("teleportgraveyard", Rank.GAMEMASTER, "/TeleportGraveyard (Grave ID)", Carg.INT);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        FastGraveyard graveyardData = GraveyardManager.getGraveyard(Integer.parseInt(args[0]));
        if(graveyardData != null) {
            sender.teleport(graveyardData.toLocation());
            WorldHelper.playEffect(graveyardData.toLocation(), Effect.MOBSPAWNER_FLAMES);
            WorldHelper.playEffect(graveyardData.toLocation().clone().add(0, 1, 0), Effect.MOBSPAWNER_FLAMES);
            WorldHelper.playSound(graveyardData.toLocation(), Sound.ENDERMAN_TELEPORT);
            CCGraveyard.FRM.TP.send(sender, args[0]);
        } else {
            CCGraveyard.FRM.TP_FAILED.send(sender, args[0]);
        }
    }
}
