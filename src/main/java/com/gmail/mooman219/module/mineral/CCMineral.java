package com.gmail.mooman219.module.mineral;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.core.CowComponent;
import com.gmail.mooman219.module.mineral.command.AddMineral;
import com.gmail.mooman219.module.mineral.command.ClearMinerals;
import com.gmail.mooman219.module.mineral.command.ListMinerals;
import com.gmail.mooman219.module.mineral.command.RemoveMineral;
import com.gmail.mooman219.module.mineral.command.RevertMinerals;
import com.gmail.mooman219.module.mineral.command.TotalMinerals;
import com.gmail.mooman219.module.mineral.listener.ListenerBlock;
import com.gmail.mooman219.module.mineral.listener.ListenerTime;
import com.gmail.mooman219.module.mineral.store.CSMineral;
import com.gmail.mooman219.module.mineral.store.StoreMineral;

public class CCMineral implements CowComponent {
    public final Loader plugin;
    public StoreMineral storeMineral;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[CC][M][Mineral] ";

    public ListenerBlock listenerBlock;
    public ListenerTime listenerTime;

    public CCMineral(Loader plugin){
        this.plugin = plugin;
    }

    @Override
    public void onEnable(){
        storeMineral = new StoreMineral();
        Loader.info(cast + "Loaded " + storeMineral.fileName);
        Loader.info(cast + "Starting MineralManager");
        MineralManager.start();

        listenerBlock = new ListenerBlock();
        listenerTime = new ListenerTime();

        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(listenerBlock, plugin);
        pm.registerEvents(listenerTime, plugin);

        Loader.info(cast + "Enabled");
    }

    @Override
    public void onDisable(){
        Loader.info(cast + "Stopping MineralManager");
        MineralManager.stop();
        Loader.info(cast + "Saving " + storeMineral.fileName);
        storeMineral.save();
        Loader.info(cast + "Disabled");
    }

    @Override
    public void registerConfigurationSerialization() {
        ConfigurationSerialization.registerClass(CSMineral.class, "CSMineral");
    }

    @Override
    public void loadCommands() {
        plugin.getCommand("addmineral").setExecutor(new AddMineral(this));
        plugin.getCommand("clearminerals").setExecutor(new ClearMinerals(this));
        plugin.getCommand("listminerals").setExecutor(new ListMinerals());
        plugin.getCommand("removemineral").setExecutor(new RemoveMineral(this));
        plugin.getCommand("revertminerals").setExecutor(new RevertMinerals());
        plugin.getCommand("totalminerals").setExecutor(new TotalMinerals());
    }
}
