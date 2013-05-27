package com.gmail.mooman219.module.service.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.frame.time.TimeHelper;
import com.gmail.mooman219.frame.time.TimeType;
import com.gmail.mooman219.handler.database.CHDatabase;
import com.gmail.mooman219.handler.database.DownloadReason;
import com.gmail.mooman219.module.CDPlayer;
import com.gmail.mooman219.module.service.CCService;

public class Whois extends CCommand {
    public Whois() {
        super("whois", Rank.REGULAR, "/Whois (Playername)", Carg.OPTIONAL);
    }

    @Override
    public void processPlayer(final Player sender, final CDPlayer player, final String[] args) {
        if(args.length == 1) {
            if(args[0].length() > 16 || args[0].length() < 3) {
                CCService.FRM.WHOISERROR.send(sender, args[0]);
                return;
            }
            Player other = Bukkit.getPlayer(args[0]);
            if(other != null) {
                displayWhois(sender, CDPlayer.get(other));
                return;
            }
            player.runTask(new Runnable() {
                @Override
                public void run() {
                    CDPlayer playerData = CHDatabase.manager.downloadPlayer(args[0], DownloadReason.QUERY);
                    if(playerData == null) {
                        CCService.FRM.WHOISERROR.send(sender, args[0]);
                    } else {
                        displayWhois(sender, playerData);
                    }
                }
            });
        } else {
            displayWhois(sender, player);
        }
    }

    public void displayWhois(Player sender, CDPlayer target) {
        long currentTime = System.currentTimeMillis();
        sender.sendMessage(Chat.msgInfo + "Displaying " + target.serviceData.rank.color + target.username + Chat.GREEN + "'s data:\n" +
                Chat.lineInfo + Chat.GRAY + "Rank" + Chat.DARK_GRAY + ": " + target.serviceData.rank.color + target.serviceData.rank.name() + "\n" +
                Chat.lineInfo + Chat.GRAY + "First joined" + Chat.DARK_GRAY + ": " + Chat.WHITE + TimeHelper.getLargestType(currentTime - target.loginData.firstlogin, TimeType.MILLISECOND) + " ago\n" +
                Chat.lineInfo + Chat.GRAY + "Last login" + Chat.DARK_GRAY + ": " + Chat.WHITE + TimeHelper.getLargestType(currentTime - target.loginData.lastlogin, TimeType.MILLISECOND) + " ago" + Chat.DARK_GRAY + " (" + (target.loginData.isOnline ? Chat.DARK_GREEN + "Online" : Chat.DARK_RED + "Offline") + Chat.DARK_GRAY + ")");
        sender.sendMessage(target.loginData.isOnline ?
                Chat.lineInfo + Chat.GRAY + "Time played" + Chat.DARK_GRAY + ": " + Chat.WHITE + TimeHelper.getLargestType(currentTime - target.loginData.lastlogin + target.loginData.timeplayed, TimeType.MILLISECOND) :
                    Chat.lineInfo + Chat.GRAY + "Time played" + Chat.DARK_GRAY + ": " + Chat.WHITE + TimeHelper.getLargestType(target.loginData.timeplayed, TimeType.MILLISECOND));
    }
    // ≣ ≡ ≛ ≜
}
