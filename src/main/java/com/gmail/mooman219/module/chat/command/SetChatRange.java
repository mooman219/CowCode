package com.gmail.mooman219.module.chat.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.handler.config.CHConfig;
import com.gmail.mooman219.handler.config.ConfigGlobal;
import com.gmail.mooman219.module.CDPlayer;
import com.gmail.mooman219.module.chat.CCChat;

public class SetChatRange extends CCommand {
    public SetChatRange() {
        super("setchatrange", Rank.REGULAR, "/SetChatRange (Range)", Carg.INT);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        int oldRange = ConfigGlobal.chatRadius;
        ConfigGlobal.chatRadius = Integer.parseInt(args[0]);
        CHConfig.configGlobal.save();
        CCChat.FRM.SETRANGE.send(sender, oldRange, ConfigGlobal.chatRadius);

    }
}
