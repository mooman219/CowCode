package com.gmail.mooman219.frame.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bullbukkit.CDPlayer;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.Chat;

public class CCommand implements CommandExecutor {
    public String command;
    public CommandUsage usage;
    public Rank requiredRank;
    public String help;

    public CCommand(String command, Rank requiredRank, String help, Carg... arguments) {
        this.command = command;
        this.usage = new CommandUsage(arguments);
        this.requiredRank = requiredRank;
        this.help = help;
    }

    @Override
    public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!usage.validate(args)) {
            sender.sendMessage(Chat.msgError + "Invalid Usage.\n" + Chat.lineError + "Correct Usage" + Chat.DARK_GRAY + ": " + Chat.WHITE + help);
        } else if(sender instanceof Player) {
            Player player = (Player) sender;
            CDPlayer playerData = CDPlayer.get(player);
            if(playerData.serviceData.rank.index >= requiredRank.index) {
                processPlayer(player, playerData, args);
            } else {
                sender.sendMessage(Chat.msgError + "Insufficient rank.");
            }
        } else {
            processConsole(sender, args);
        }
        return true;
    }

    public void processPlayer(final Player sender, final CDPlayer playerData, final String[] args) {
        sender.sendMessage(Chat.msgError + "Unable to use command");
    }

    public void processConsole(final CommandSender sender, final String[] args) {
        sender.sendMessage(Chat.msgError + "Unable to use command");
    }
}
