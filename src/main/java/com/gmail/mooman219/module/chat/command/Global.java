package com.gmail.mooman219.module.chat.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCArg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.CDPlayer;

public class Global extends CCommand {
    public Global() {
        super(Rank.REGULAR, "/Global (Message)", CCArg.MESSAGE);
    }

    @Override
    public void processPlayer(final Player sender, final CDPlayer playerData, final String[] args) {
        CDPlayer.get(sender).chat("!" + TextHelper.merge(args, 0));
    }
}
