package com.gmail.mooman219.module.region.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.module.region.RegionManager;
import com.gmail.mooman219.module.region.CCRegion;
import com.gmail.mooman219.module.region.store.BasicRegion;

public class Region extends CCommand {
    public Region() {
        super("region", Rank.REGULAR, "/Region (id)", Carg.OPTIONAL);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        if(args.length > 0) {
            BasicRegion region = RegionManager.getRegion(args[0]);
            if(region != null) {
                sendInfo(sender, region);
            } else {
                CCRegion.MSG.NONEXISTS.send(sender);
            }
        } else {
            sendInfo(sender, playerData.region.getRegion());
        }
    }

    public static void sendInfo(CommandSender sender, BasicRegion region) {
        sender.sendMessage(
        Chat.msgInfo + "Region information for [" + Chat.GRAY + region.getID() + Chat.GREEN + "]" + Chat.DARK_GREEN + ":" + "\n" +
        Chat.lineInfo + Chat.GRAY + "ID" + Chat.DARK_GRAY + ": " + Chat.WHITE + region.getID() + "\n" +
        Chat.lineInfo + Chat.GRAY + "Name" + Chat.DARK_GRAY + ": " + Chat.WHITE + region.getName() + "\n" +
        Chat.lineInfo + Chat.GRAY + "Description" + Chat.DARK_GRAY + ": " + Chat.WHITE + region.getDescription() + "\n" +
        Chat.lineInfo + Chat.GRAY + "Locked" + Chat.DARK_GRAY + ": " + Chat.WHITE + region.isLocked() + "\n" +
        Chat.lineInfo + Chat.GRAY + "Combat" + Chat.DARK_GRAY + ": " + Chat.WHITE + region.getCombatType().getColoredName()
        );
    }
}
