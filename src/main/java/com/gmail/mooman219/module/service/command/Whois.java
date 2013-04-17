package com.gmail.mooman219.module.service.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.frame.time.TimeHelper;
import com.gmail.mooman219.frame.time.TimeType;
import com.gmail.mooman219.handler.task.CHTask;
import com.gmail.mooman219.module.DLPlayer;
import com.gmail.mooman219.module.service.CMService;
import com.gmail.mooman219.module.service.task.WhoisTask;

public class Whois extends CCommand {
    public Whois() {
        super("optional", Rank.REGULAR, "/Whois (Playername)");
    }

    @Override
    public void processPlayer(Player sender, DLPlayer playerData, String[] args) {
        if(args.length == 1) {
            if(args[0].length() > 16 || args[0].length() < 3) {
                TextHelper.message(sender, CMService.F_WHOIS_NOEXIST, args[0]);
                return;
            }
            Player player = Bukkit.getPlayer(args[0]);
            if(player != null) {
                displayWhois(sender, DLPlayer.get(player));
                return;
            }
            CHTask.manager.runAsyncPluginTask(WhoisTask.get(sender, args[0]));
        } else {
            displayWhois(sender, playerData);
        }
    }

    public static void displayWhois(Player sender, DLPlayer target) {
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
