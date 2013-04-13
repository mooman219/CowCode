package com.gmail.mooman219.module.chat.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.chat.CCChat;
import com.gmail.mooman219.module.service.PlayerData;

public class Reply extends CCommand {
    public Reply() {
        super("message", Rank.REGULAR, "/Reply (Message)");
    }

    @Override
    public void processPlayer(Player sender, PlayerData playerData, String[] args) {
        CCChat.queueChat.put(sender, "@ " + TextHelper.merge(args));
    }
}
