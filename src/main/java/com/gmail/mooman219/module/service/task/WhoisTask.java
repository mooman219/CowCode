package com.gmail.mooman219.module.service.task;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.database.mongo.DownloadType;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.handler.databse.CHDatabase;
import com.gmail.mooman219.handler.task.type.CCTask;
import com.gmail.mooman219.module.CDPlayer;
import com.gmail.mooman219.module.service.CMService;
import com.gmail.mooman219.module.service.command.Whois;

public class WhoisTask extends CCTask {
    public final Player sender;
    public final String username;

    public WhoisTask(Player sender, String username) {
        this.sender = sender;
        this.username = username;
    }

    public static WhoisTask get(Player sender, String username) {
        return new WhoisTask(sender, username);
    }

    @Override
    public void run() {
        CDPlayer playerData = CHDatabase.manager.downloadPlayerData(username, DownloadType.QUERY);
        if(playerData == null) {
            TextHelper.message(sender, CMService.F_WHOIS_NOEXIST, username);
        } else {
            Whois.displayWhois(sender, playerData);
        }
    }
}