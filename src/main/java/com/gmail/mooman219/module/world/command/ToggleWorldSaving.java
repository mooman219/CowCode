package com.gmail.mooman219.module.world.command;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.handler.config.CHConfig;
import com.gmail.mooman219.handler.config.store.ConfigGlobal;
import com.gmail.mooman219.module.world.CCWorld;

public class ToggleWorldSaving extends CCommand {
    public ToggleWorldSaving() {
        super("toggleworldsaving", Rank.DEVELOPER, "/ToggleWorldSaving");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        processConsole(sender, args);
    }

    @Override
    public void processConsole(final CommandSender sender, final String[] args) {
        ConfigGlobal.module.world.disableWorldSaving = !ConfigGlobal.module.world.disableWorldSaving;
        for(World world : WorldHelper.getWorlds()) {
            world.setAutoSave(ConfigGlobal.module.world.disableWorldSaving);
        }
        CHConfig.configGlobal.save();
        CCWorld.FRM.WORLDSAVETOGGLE.send(sender, ConfigGlobal.module.world.disableWorldSaving);
    }
}
