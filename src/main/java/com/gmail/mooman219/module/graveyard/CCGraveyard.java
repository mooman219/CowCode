package com.gmail.mooman219.module.graveyard;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.core.Module;
import com.gmail.mooman219.module.graveyard.command.AddGraveyard;
import com.gmail.mooman219.module.graveyard.command.ClearGraveyards;
import com.gmail.mooman219.module.graveyard.command.ListGraveyards;
import com.gmail.mooman219.module.graveyard.command.RemoveGraveyard;
import com.gmail.mooman219.module.graveyard.command.TeleportClosestGraveyard;
import com.gmail.mooman219.module.graveyard.command.TeleportGraveyard;
import com.gmail.mooman219.module.graveyard.command.TotalGraveyards;
import com.gmail.mooman219.module.graveyard.listener.ListenerPlayer;
import com.gmail.mooman219.module.graveyard.store.CSGraveyard;
import com.gmail.mooman219.module.graveyard.store.StoreGraveyard;

public class CCGraveyard implements Module {
    public final Loader plugin;
    public StoreGraveyard storeGraveyard;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[CC][M][Graveyard] ";

    public ListenerPlayer listenerPlayer;

    public CCGraveyard(Loader plugin){
        this.plugin = plugin;
    }

    @Override
    public void onEnable(){
        storeGraveyard = new StoreGraveyard();
        Loader.info(cast + "Loaded " + storeGraveyard.fileName);

        listenerPlayer = new ListenerPlayer();

        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(listenerPlayer, plugin);

        Loader.info(cast + "Enabled");
    }

    @Override
    public void onDisable(){
        Loader.info(cast + "Saving " + storeGraveyard.fileName);
        storeGraveyard.save();
        Loader.info(cast + "Disabled");
    }

    @Override
    public void registerConfigurationSerialization() {
        ConfigurationSerialization.registerClass(CSGraveyard.class, "CSGraveyard");
    }

    @Override
    public void loadCommands() {
        plugin.getCommand("addgraveyard").setExecutor(new AddGraveyard(this));
        plugin.getCommand("cleargraveyards").setExecutor(new ClearGraveyards(this));
        plugin.getCommand("totalgraveyards").setExecutor(new TotalGraveyards());
        plugin.getCommand("teleportclosestgraveyard").setExecutor(new TeleportClosestGraveyard());
        plugin.getCommand("removegraveyard").setExecutor(new RemoveGraveyard(this));
        plugin.getCommand("listgraveyards").setExecutor(new ListGraveyards());
        plugin.getCommand("teleportgraveyard").setExecutor(new TeleportGraveyard());
    }
}
