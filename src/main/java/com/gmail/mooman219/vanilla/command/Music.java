package com.gmail.mooman219.vanilla.command;

import org.bukkit.Effect;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.MathHelper;
import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.vanilla.CCVanilla;

public class Music extends CCommand {
    public Music() {
        super("music", Rank.DEVELOPER, "/Music (id)", Carg.INT);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        int music = MathHelper.toInt(args[0]) + 2256;
        WorldHelper.playEffect(sender.getLocation(), Effect.RECORD_PLAY, music);
        CCVanilla.FRM.MUSIC.send(sender, music);
    }
}
