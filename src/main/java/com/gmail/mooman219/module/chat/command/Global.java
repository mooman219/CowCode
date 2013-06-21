package com.gmail.mooman219.module.chat.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;

public class Global extends CCommand {
    public Global() {
        super("global", Rank.REGULAR, "/Global (Message)", Carg.MESSAGE);
    }

    @Override
    public void processPlayer(final Player sender, final CDPlayer playerData, final String[] args) {
        playerData.chat("!" + TextHelper.merge(args, 0));
    }
}
