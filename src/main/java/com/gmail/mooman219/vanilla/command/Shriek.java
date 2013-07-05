package com.gmail.mooman219.vanilla.command;

import org.bukkit.Effect;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.NumberHelper;
import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.vanilla.CCVanilla;

public class Shriek extends CCommand {
    public Shriek() {
        super("shriek", Rank.DEVELOPER, "/Shriek");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        WorldHelper.playEffect(sender.getLocation(), Effect.GHAST_SHRIEK, NumberHelper.nextRandom().nextInt(51));
        CCVanilla.MSG.SHRIEK.send(sender);
    }
}
