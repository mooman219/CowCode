package com.gmail.mooman219.module.chat.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.CDPlayer;

public class Reply extends CCommand {
    public Reply() {
        super("reply", Rank.REGULAR, "/Reply (Message)", Carg.MESSAGE);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        CDPlayer.get(sender).chat("@ " + TextHelper.merge(args, 0));
    }
}
