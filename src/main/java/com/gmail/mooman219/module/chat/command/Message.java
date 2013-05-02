package com.gmail.mooman219.module.chat.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.CDPlayer;

public class Message extends CCommand {
    public Message() {
        super("string message", Rank.REGULAR, "/Message (Player) (Message)");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        CDPlayer.get(sender).chat("@" + args[0] + TextHelper.merge(args, 1));
    }
}
