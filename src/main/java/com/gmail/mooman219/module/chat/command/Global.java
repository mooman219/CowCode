package com.gmail.mooman219.module.chat.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.handler.task.CHTask;
import com.gmail.mooman219.module.DLPlayer;
import com.gmail.mooman219.module.chat.task.ChatTask;

public class Global extends CCommand {
    public Global() {
        super("message", Rank.REGULAR, "/Global (Message)");
    }

    @Override
    public void processPlayer(Player sender, DLPlayer playerData, String[] args) {
        CHTask.manager.runAsyncPluginTask(ChatTask.get(sender, "!" + TextHelper.merge(args)));
    }
}
