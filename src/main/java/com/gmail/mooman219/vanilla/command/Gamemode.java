package com.gmail.mooman219.vanilla.command;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.vanilla.CCVanilla;

public class Gamemode extends CCommand {
    public Gamemode() {
        super("gamemode", Rank.DEVELOPER, "/Gamemode");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        sender.setGameMode(sender.getGameMode() == GameMode.CREATIVE ? GameMode.SURVIVAL : GameMode.CREATIVE); 
        CCVanilla.FRM.GAMEMODE.send(sender, sender.getGameMode().toString());
    }
}
