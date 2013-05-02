package com.gmail.mooman219.module.chat.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.handler.task.CHTask;
import com.gmail.mooman219.module.CDPlayer;
import com.gmail.mooman219.module.chat.task.ChatTask;

public class Message extends CCommand {
    public Message() {
        super("string message", Rank.REGULAR, "/Message (Player) (Message)");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        CHTask.manager.runPluginAsyncTask(ChatTask.get(sender, "@" + args[0] + " " + TextHelper.merge(args, 1)));
    }
}
