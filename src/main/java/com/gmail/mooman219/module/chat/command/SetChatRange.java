package com.gmail.mooman219.module.chat.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.math.NumberHelper;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.handler.config.CHConfig;
import com.gmail.mooman219.handler.config.store.ConfigGlobal;
import com.gmail.mooman219.module.chat.CCChat;

public class SetChatRange extends CCommand {
    public SetChatRange() {
        super("setchatrange", Rank.GAMEMASTER, "/SetChatRange (Range)", Carg.INT);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        int oldRange = ConfigGlobal.module.chat.radius;
        ConfigGlobal.module.chat.radius = NumberHelper.toInt(Math.pow(Integer.parseInt(args[0]), 2));
        CHConfig.configGlobal.save();
        CCChat.FRM.SETRANGE.send(sender, oldRange, ConfigGlobal.module.chat.radius);

    }
}
