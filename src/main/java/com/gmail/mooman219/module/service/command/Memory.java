package com.gmail.mooman219.module.service.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.Chat;

public class Memory extends CCommand {
    public Memory() {
        super("memory", Rank.REGULAR, "/Memory");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        displayMemoryUsage(sender);
    }

    @Override
    public void processConsole(CommandSender sender, String[] args) {
        displayMemoryUsage(sender);
    }

    public static void displayMemoryUsage(CommandSender sender) {
        double memUsed = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L;
        double memMax = Runtime.getRuntime().maxMemory() / 1048576L;
        double memFree = memMax - memUsed;
        double percentageFree = 100.0D / memMax * memFree;
        sender.sendMessage(Chat.msgPassive + Chat.GRAY + "Used" + Chat.DARK_GRAY + ": " + Chat.GOLD + memUsed + Chat.GRAY + " Max" + Chat.DARK_GRAY + ": " + Chat.GOLD + memMax + Chat.GRAY + " Free" + Chat.DARK_GRAY + ": " + Chat.GOLD + memFree + Chat.GRAY + " %Free" + Chat.DARK_GRAY + ": " + Chat.GOLD + ((int)percentageFree));
    }
}
