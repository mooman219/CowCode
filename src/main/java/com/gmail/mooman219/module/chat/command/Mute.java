package com.gmail.mooman219.module.chat.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.NumberHelper;
import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.time.TimeHelper;
import com.gmail.mooman219.frame.time.TimeString;
import com.gmail.mooman219.frame.time.TimeType;
import com.gmail.mooman219.module.chat.CCChat;

public class Mute extends CCommand {
    public Mute() {
        super("mute", Rank.MODERATOR, "/mute (Player) (Amount) (TimeType)", Carg.STRING, Carg.INT, Carg.STRING);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        Player target = Bukkit.getPlayer(args[0]);
        if(target != null) {
            CDPlayer targetData = CDPlayer.get(target);
            int amount = NumberHelper.toInt(args[1]);
            TimeType type = TimeHelper.getType(args[2]);
            TimeString mutedUntilString = new TimeString(type, amount);
            long mutedUntil = TimeHelper.time() + TimeHelper.convert(amount, type, TimeType.MILLISECOND);

            targetData.chat.mutedUntil = mutedUntil;
            CCChat.FRM.MUTED_TARGET.send(targetData, mutedUntilString.toString());
            CCChat.FRM.MUTED_SENDER.send(playerData, target.getName(), mutedUntilString.toString());
        } else {
            CCChat.FRM.PLAYER_NOT_FOUND.send(playerData, args[0]);
        }
    }
}
