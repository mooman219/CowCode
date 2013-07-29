package com.gmail.mooman219.module.service.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.handler.config.CHConfig;
import com.gmail.mooman219.module.service.CCService;

public class LoadConfig extends CCommand {
    public LoadConfig() {
        super("loadconfig", Rank.DEVELOPER, "/LoadConfig");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        reload(sender);
    }

    @Override
    public void processConsole(CommandSender sender, String[] args) {
        reload(sender);
    }

    public void reload(CommandSender sender) {
        CHConfig.getConfig().load();
        CCService.MSG.CONFIGRELOAD.send(sender);
    }
}
