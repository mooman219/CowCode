package com.gmail.mooman219.module.chat.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.handler.config.CHConfig;
import com.gmail.mooman219.handler.config.ConfigGlobal;
import com.gmail.mooman219.module.CDPlayer;
import com.gmail.mooman219.module.chat.CMChat;

public class SetChatRange extends CCommand {
    public SetChatRange() {
        super("number", Rank.REGULAR, "/SetChatRange (Range)");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        int oldRange = ConfigGlobal.chatRadius;
        ConfigGlobal.chatRadius = Integer.parseInt(args[0]);
        CHConfig.configGlobal.save();
        TextHelper.message(sender, CMChat.F_SETRANGE, oldRange, ConfigGlobal.chatRadius);
        
    }
}
