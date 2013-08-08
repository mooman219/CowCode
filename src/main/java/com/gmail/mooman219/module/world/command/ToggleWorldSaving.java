package com.gmail.mooman219.module.world.command;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.world.CCWorld;
import com.gmail.mooman219.module.world.store.ConfigWorld;

public class ToggleWorldSaving extends CCommand {
    private final CCWorld module;
    public ToggleWorldSaving(CCWorld module) {
        super("toggleworldsaving", Rank.DEVELOPER, "/ToggleWorldSaving");
        this.module = module;
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        processConsole(sender, args);
    }

    @Override
    public void processConsole(final CommandSender sender, final String[] args) {
        ConfigWorld.getData().disableWorldSaving = !ConfigWorld.getData().disableWorldSaving;
        for(World world : WorldHelper.getWorlds()) {
            world.setAutoSave(ConfigWorld.getData().disableWorldSaving);
        }
        module.configWorld.save();
        CCWorld.FRM.WORLDSAVETOGGLE.send(sender, !ConfigWorld.getData().disableWorldSaving);
    }
}
